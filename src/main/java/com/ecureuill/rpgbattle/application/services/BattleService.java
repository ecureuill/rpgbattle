package com.ecureuill.rpgbattle.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.dtos.BattleSelectCharacterRequest;
import com.ecureuill.rpgbattle.application.exceptions.BattleNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.BattleStateException;
import com.ecureuill.rpgbattle.application.exceptions.CharacterNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.application.services.specifications.QueryParamsSpecification;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.CreatedBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.InitiativeBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.NotCreatedBattleState;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.infrastructure.repositories.BattleRepository;
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

  public Battle selectCharacter(UUID battleId, String playerUsername, @Valid BattleSelectCharacterRequest data) throws BattleNotFoundException, PlayerNotFoundException, CharacterNotFoundException, BattleStateException {
    Battle battle = findBattleById(battleId);
    Character character = characterService.getCharacterBySpecie(data.specie());

    try {
      ((CreatedBattleState)battle.getState()).addCharacter(battle, playerUsername, character);
    } catch (ClassCastException e) {
        throw new BattleStateException("This operation is not allowed while battle is in " + battle.getStage().getName());
    }

    battleRepository.save(battle);
    return battle;
  }

  public Battle determineInitiative(UUID battleId) throws BattleStateException, BattleNotFoundException{
    Battle battle = findBattleById(battleId);

    try {
      ((InitiativeBattleState)battle.getState()).determineInitiative(battle);
    } catch (ClassCastException e) {
        throw new BattleStateException("This operation is not allowed while battle is in " + battle.getStage().getName());
    }

    battleRepository.save(battle);
    return battle;
  }

  private Battle findBattleById(UUID battleId) throws BattleNotFoundException {
    return battleRepository.findById(battleId).orElseThrow(() -> new BattleNotFoundException(battleId));
  }

}
