package com.ecureuill.rpgbattle.application.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.dtos.BattleSelectCharacterRequest;
import com.ecureuill.rpgbattle.application.exceptions.AuthorizationException;
import com.ecureuill.rpgbattle.application.exceptions.BattleNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.BattleStateException;
import com.ecureuill.rpgbattle.application.exceptions.CharacterNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.exceptions.NoTurnActiveException;
import com.ecureuill.rpgbattle.application.exceptions.NotPlayerTurnException;
import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.TurnNotFoundException;
import com.ecureuill.rpgbattle.application.services.specifications.QueryParamsSpecification;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.CreatedBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.EndBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.InitiativeBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.NotCreatedBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.TurnsBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.EndTurnState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnStateType;
import com.ecureuill.rpgbattle.infrastructure.repositories.BattleRepository;
import com.ecureuill.rpgbattle.infrastructure.security.AuthenticatedUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleService {
  private final BattleRepository battleRepository;
  private final UserService userService;
  private final CharacterService characterService;
  private final List<QueryParamsSpecification> specifications;
  
  public Battle createBattle(BattleCreateRequest battleUsers) throws InvalidBattleParametersException, BattleStateException {
    
    if(battleUsers.playerOne().equals(battleUsers.playerTwo())){
      throw new InvalidBattleParametersException("Player One and Player Two should not be the same");
    }
    try {
      Player playerOne = new Player(null, userService.findByUsername(battleUsers.playerOne()).getUsername(), null, null);
      Player playerTwo = new Player(null, userService.findByUsername(battleUsers.playerTwo()).getUsername(), null, null);
      Battle battle = new Battle( );

      try {
        ((NotCreatedBattleState)battle.getState()).createBattle(battle, playerOne, playerTwo);
      } catch (ClassCastException e) {
        throw new BattleStateException("This operation is not allowed while battle is in " + battle.getStage().getName());
      }

      return battleRepository.save(battle);
    } catch (PlayerNotFoundException e) {
      throw new InvalidBattleParametersException(e.getMessage());
    }
  }
  
  public List<Battle> getAllBattles(MultiValueMap<String, String> queryParams) {
    
    if(queryParams.isEmpty()){
      return battleRepository.findAll();
    }

    for (QueryParamsSpecification specification : specifications) {
      if(specification.isSatisfiedBy(queryParams)) {
        return specification.getResults(queryParams);
      }
    }
    
    return new ArrayList<>();
  }

  public Battle getBattleById(UUID uuid) throws BattleNotFoundException {
    return battleRepository.findById(uuid).orElseThrow(() -> new BattleNotFoundException(uuid));
  }

  public Battle selectCharacter(UUID battleId, String playerUsername, @Valid BattleSelectCharacterRequest data) throws BattleNotFoundException, PlayerNotFoundException, CharacterNotFoundException, BattleStateException, AuthorizationException {
    Battle battle = findBattleById(battleId);
    SelectedCharacter character = new SelectedCharacter(characterService.getCharacterBySpecie(data.specie()));

    try {
      ((CreatedBattleState)battle.getState()).addCharacter(battle, playerUsername, character);
    } catch (ClassCastException e) {
        throw new BattleStateException("This operation is not allowed while battle is in " + battle.getStage().getName());
    }

    validatePlayer(playerUsername);

    battleRepository.save(battle);
    return battle;
  }

  public Battle determineInitiative(UUID battleId) throws BattleStateException, BattleNotFoundException, AuthorizationException{
    Battle battle = findBattleById(battleId);

    try {
      ((InitiativeBattleState)battle.getState()).determineInitiative(battle);
    } catch (ClassCastException e) {
        throw new BattleStateException("This operation is not allowed while battle is in " + battle.getStage().getName());
    }

    validatePlayer(battle.getPlayers().get(0).getPlayer().getUsername(), battle.getPlayers().get(1).getPlayer().getUsername());

    battleRepository.save(battle);
    return battle;
  }
  
  public Battle takeTurns(UUID battleId) throws BattleNotFoundException, BattleStateException, NoTurnActiveException, TurnNotFoundException, NotPlayerTurnException {
    Battle battle = findBattleById(battleId);

    
    validateBattleState(battle);
    TurnsBattleState state = (TurnsBattleState) battle.getState();
    
    validatePlayerTurn(battle);

    if(isTurnNotInitiated(battle)){
      startNewTurn(battle);
    }

    if(isTurnOver(battle)){
      startNewTurn(battle);
      battle.setLastTurnTime(LocalDateTime.now());
    }

    state.turnAction(battle);
    battleRepository.save(battle);

    if(isBattleOver(battle)){
      endedBattle(battle);
    }

    battleRepository.save(battle);
    
    return battle;   
  }

  private Boolean isBattleOver(Battle battle){
    return battle.getState() instanceof EndBattleState;
  }

  private void endedBattle(Battle battle){
    battle.setEndTime(LocalDateTime.now());
  }
  private void startNewTurn(Battle battle){
    Turn turn = new Turn();
    turn.setTurnSequence(battle.getTurnsSequence() + 1);
    battle.getTurns().add(turn);
    battle.setTurnsSequence(battle.getTurnsSequence() + 1);
    battle = battleRepository.save(battle);
    battle.setCurrentTurnId(findActiveTurn(battle));
  }
  private Boolean isTurnNotInitiated(Battle battle){
    return battle.getCurrentTurnId() == null;
  }

  private Boolean isTurnOver(Battle battle){
    Optional<Turn> optionalTurn = battle.getCurrentTurn();
    
    if(optionalTurn.isEmpty()){
      return false;
    }

    return optionalTurn.get().getState() instanceof EndTurnState;
  }

  private void validateBattleState(Battle battle) throws BattleStateException{
    if(!(battle.getState() instanceof TurnsBattleState)){
      throw new BattleStateException("This operation is not allowed while battle is in " + battle.getStage().getName());
    }
  }
  
  private Battle findBattleById(UUID battleId) throws BattleNotFoundException {
    return battleRepository.findById(battleId).orElseThrow(() -> new BattleNotFoundException(battleId));
  }

  private UUID findActiveTurn(Battle battle) {
    return battle.getTurns().stream().filter(t -> t.getStateType() != TurnStateType.IS_END).findFirst().get().getId();
  }

  private void validatePlayerTurn(Battle battle) throws NotPlayerTurnException {
    if(!battle.getPlayers().get(battle.getPlayerTurn()).getPlayer().getUsername().equals(AuthenticatedUser.getUsername())){
      throw new NotPlayerTurnException(AuthenticatedUser.getUsername());
    }
  }

  private void validatePlayer(String playerUsername) throws AuthorizationException {
      if(!playerUsername.equals(AuthenticatedUser.getUsername())){
        throw new AuthorizationException(AuthenticatedUser.getUsername());
    }
  }

  private void validatePlayer(String playerOneUsername, String playerTwoUsername) throws AuthorizationException {
    String authUser = AuthenticatedUser.getUsername();

    if(!playerOneUsername.equals(authUser) && !playerTwoUsername.equals(authUser)){
      throw new AuthorizationException(AuthenticatedUser.getUsername());
    }

  }

}
