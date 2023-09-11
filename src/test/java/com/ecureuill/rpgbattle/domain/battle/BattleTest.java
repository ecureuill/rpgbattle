package com.ecureuill.rpgbattle.domain.battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.domain.dice.Dice;
import com.ecureuill.rpgbattle.utils.DataFaker;
import com.ecureuill.rpgbattle.utils.DataFakerProvider;

@ExtendWith(MockitoExtension.class)
public class BattleTest {
  
  private final DataFaker dataFaker = DataFakerProvider.getInstace(); 

  @DisplayName("Should stage be CHARACTER_SELECTION when players has no character")
  @Test
  void testBattle() {
    Battle battle =  dataFaker.generateBattle(true);
    Assertions.assertEquals(Stage.CHARACTER_SELECTION, battle.getStage());
  }

  @DisplayName("Should stage be CHARACTER_SELECTION when only one player add character")
  @Test
  void testAddCharacter_CharacterSelection() {
    Battle battle =  dataFaker.generateBattle(true);
    battle.addCharacter(battle.getPlayers().get(0).getPlayer().getUsername(), dataFaker.generateCharacter());
    Assertions.assertEquals(Stage.CHARACTER_SELECTION, battle.getStage());
  }

  @DisplayName("Should stage be INITIATIVE when all players add character")
  @Test
  void testAddCharacter_Initiative() {
    Battle battle =  dataFaker.generateBattle(true);
    battle.addCharacter(battle.getPlayers().get(0).getPlayer().getUsername(), dataFaker.generateCharacter());
    battle.addCharacter(battle.getPlayers().get(1).getPlayer().getUsername(), dataFaker.generateCharacter());
    Assertions.assertEquals(Stage.INITIATIVE, battle.getStage());
  }
  
  @DisplayName("Should return playerOne when playerOne dice is greater then playerTwo dice")
  @Test
  void testInitiative_PlayerOne() {
    Dice dice = Mockito.mock(Dice.class);
    Mockito.when(dice.roll()).thenReturn(6, 1);
    Battle battle =  dataFaker.generateBattle();
    battle.setDice(dice);
    var returnedPlayer = battle.initiative();

    Assertions.assertEquals(battle.getPlayers().get(0).getPlayer(), returnedPlayer);
  }

  @DisplayName("Should return playerTwo when playerTwo dice is greaten then playerOne dice")
  @Test
  void testInitiative_PlayerTwo() {
    Dice dice = Mockito.mock(Dice.class);
    Mockito.when(dice.roll()).thenReturn(1, 6);
    Battle battle =  dataFaker.generateBattle();
    battle.setDice(dice);
    var returnedPlayer = battle.initiative();

    Assertions.assertEquals(battle.getPlayers().get(1).getPlayer(), returnedPlayer);
  }

  @DisplayName("Should return null when dices are equal")
  @Test
  void testInitiative_Equal() {
    Dice dice = Mockito.mock(Dice.class);
    Mockito.when(dice.roll()).thenReturn(1, 1);
    Battle battle =  dataFaker.generateBattle(); 
    battle.setDice(dice);
    var returnedPlayer = battle.initiative();
    
    Assertions.assertNull(returnedPlayer);
  }

  @DisplayName("Should stage be INITIATIVE when dices are equal")
  @Test
  void testInitiative() {
    Dice dice = Mockito.mock(Dice.class);
    Mockito.when(dice.roll()).thenReturn(1, 1);
    Battle battle =  dataFaker.generateBattle(true);
    battle.setDice(dice);
    battle.addCharacter(battle.getPlayers().get(0).getPlayer().getUsername(), dataFaker.generateCharacter());
    battle.addCharacter(battle.getPlayers().get(1).getPlayer().getUsername(), dataFaker.generateCharacter());
    battle.initiative();

    Assertions.assertEquals(Stage.INITIATIVE, battle.getStage());
  }

  @DisplayName("Should stage be TURNS when dices are not equal")
  @Test
  void testInitiative_Turns() {
    Dice dice = Mockito.mock(Dice.class);
    Mockito.doReturn(1, 2).when(dice).roll();
    Battle battle =  dataFaker.generateBattle(true);
    battle.setDice(dice);
    battle.addCharacter(battle.getPlayers().get(0).getPlayer().getUsername(), dataFaker.generateCharacter());
    battle.addCharacter(battle.getPlayers().get(1).getPlayer().getUsername(), dataFaker.generateCharacter());
    battle.initiative();
    Assertions.assertEquals(Stage.TURNS, battle.getStage());
  }

}
