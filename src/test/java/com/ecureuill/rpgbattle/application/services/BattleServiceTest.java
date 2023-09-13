package com.ecureuill.rpgbattle.application.services;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.dtos.BattleSelectCharacterRequest;
import com.ecureuill.rpgbattle.application.exceptions.BattleNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.BattleStateException;
import com.ecureuill.rpgbattle.application.exceptions.CharacterNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Stage;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.CreatedBattleState;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.InitiativeBattleState;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.user.User;
import com.ecureuill.rpgbattle.infrastructure.repositories.BattleRepository;
import com.ecureuill.rpgbattle.utils.DataFaker;
import com.ecureuill.rpgbattle.utils.DataFakerProvider;

@ExtendWith(MockitoExtension.class)
public class BattleServiceTest {
  @Mock
  private BattleRepository battleRepository;
  @Mock
  private UserService userService;
  @Mock
  private CharacterService characterService;
  @InjectMocks
  private BattleService battleService;
  
  @DisplayName("Should create a battle when valid data is provided")
  @Test
  void testCreateBattle() throws PlayerNotFoundException, InvalidBattleParametersException, BattleStateException {
    DataFaker faker = DataFakerProvider.getInstace();
    BattleCreateRequest request = faker.generateBattleCreateRequest();
    Player playerOne = faker.generatePlayer(true);
    Player playerTwo = faker.generatePlayer(true);

    Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(new User(null, playerOne.getUsername(), null, null), new User(null, playerTwo.getUsername(), null, null));
  
    battleService.createBattle(request);

    ArgumentCaptor<Battle> battleArgumentCaptor = ArgumentCaptor.forClass(Battle.class);
    Mockito.verify(battleRepository, Mockito.times(1)).save(battleArgumentCaptor.capture());
    var result = battleArgumentCaptor.getValue();
    Mockito.verify(userService, Mockito.times(2)).findByUsername(Mockito.anyString());
    Assertions.assertNotNull(result);
    Assertions.assertEquals(2, result.getPlayers().size());
    Assertions.assertEquals(playerOne.getUsername(), result.getPlayers().get(0).getPlayer().getUsername());
    Assertions.assertEquals(null, result.getPlayers().get(0).getPlayer().getCharacter());
    Assertions.assertEquals(playerTwo.getUsername(), result.getPlayers().get(1).getPlayer().getUsername());
    Assertions.assertEquals(playerTwo.getUsername(), result.getPlayers().get(1).getPlayer().getUsername());
    Assertions.assertNotNull(result.getStartTime());
    Assertions.assertEquals(CreatedBattleState.class, result.getState().getClass());
    Assertions.assertEquals(Stage.CHARACTER_SELECTION, result.getStage());
  }

  @DisplayName("Should throw PlayerNotFoundException when invalid username is provided")
  @Test
  void testCreateBattle_PlayerNotFound() throws PlayerNotFoundException {
    BattleCreateRequest request = DataFakerProvider.getInstace().generateBattleCreateRequest();

    Mockito.when(userService.findByUsername(Mockito.anyString())).thenThrow(PlayerNotFoundException.class);

    Assertions.assertThrows(InvalidBattleParametersException.class, () -> battleService.createBattle(request));

    Mockito.verify(userService, Mockito.times(1)).findByUsername(Mockito.anyString());
    Mockito.verify(battleRepository, Mockito.times(0)).save(Mockito.any(Battle.class));
  
  }

  @DisplayName("Should throw InvalidBattleParametersException when playerOne and playerTwo are the same")
  @Test
  void testCreateBattle_InvalidBattleParameters_SamePlayers() throws PlayerNotFoundException, InvalidBattleParametersException {
    BattleCreateRequest request = new BattleCreateRequest("username", "username");

    Assertions.assertThrows(InvalidBattleParametersException.class, () -> battleService.createBattle(request));

    Mockito.verify(userService, Mockito.times(0)).findByUsername(Mockito.anyString());
    Mockito.verify(battleRepository, Mockito.times(0)).save(Mockito.any(Battle.class));

  }

  @DisplayName("Should add character to the correct player of the battle")
  @Test
  void testSelectCharacter() throws CharacterNotFoundException, BattleNotFoundException, PlayerNotFoundException, BattleStateException {
    DataFaker faker = DataFakerProvider.getInstace();
    BattleSelectCharacterRequest request = new BattleSelectCharacterRequest("specie");
    Battle battle = faker.generateBattle(true);
    battle.setState(new CreatedBattleState());
    Character character = faker.generateCharacter();

    Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
    Mockito.when(characterService.getCharacterBySpecie(Mockito.anyString())).thenReturn(character);
    
    battleService.selectCharacter(UUID.randomUUID(), battle.getPlayers().get(1).getPlayer().getUsername(), request);

    ArgumentCaptor<Battle> battleArgumentCaptor = ArgumentCaptor.forClass(Battle.class);
    Mockito.verify(battleRepository, Mockito.times(1)).save(battleArgumentCaptor.capture());
    var result = battleArgumentCaptor.getValue();
    Assertions.assertEquals(character, result.getPlayers().get(1).getPlayer().getCharacter());
    Assertions.assertEquals(CreatedBattleState.class, result.getState().getClass());
  }

  @DisplayName("Should add the second character to the correct player and change battle state")
  @Test
  void testSelectCharacter_SecondCharacter() throws CharacterNotFoundException, BattleNotFoundException, PlayerNotFoundException, BattleStateException {
    DataFaker faker = DataFakerProvider.getInstace();
    BattleSelectCharacterRequest request = new BattleSelectCharacterRequest("specie");
    Battle battle = faker.generateBattle(true);
    battle.setState(new CreatedBattleState());
    Character characterOne = faker.generateCharacter();
    battle.getPlayers().get(1).getPlayer().setCharacter(characterOne);
    Character characterTwo = faker.generateCharacter();

    Mockito.when(battleRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(battle));
    Mockito.when(characterService.getCharacterBySpecie(Mockito.anyString())).thenReturn(characterTwo);
    
    battleService.selectCharacter(UUID.randomUUID(), battle.getPlayers().get(0).getPlayer().getUsername(), request);

    ArgumentCaptor<Battle> battleArgumentCaptor = ArgumentCaptor.forClass(Battle.class);
    Mockito.verify(battleRepository, Mockito.times(1)).save(battleArgumentCaptor.capture());
    var result = battleArgumentCaptor.getValue();
    Assertions.assertEquals(characterOne, result.getPlayers().get(1).getPlayer().getCharacter());
    Assertions.assertEquals(characterTwo, result.getPlayers().get(0).getPlayer().getCharacter());
    Assertions.assertEquals(InitiativeBattleState.class, result.getState().getClass());
  }
}
