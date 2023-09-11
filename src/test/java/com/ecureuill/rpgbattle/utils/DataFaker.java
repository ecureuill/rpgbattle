package com.ecureuill.rpgbattle.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.domain.character.Type;
import com.ecureuill.rpgbattle.domain.dice.Dice;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.PlayerBattle;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.github.javafaker.Faker;

public class DataFaker {
  Faker faker; 

  public DataFaker() {
    faker = new Faker(new Locale("pt-BR"));
  }

  public CharacterRequest generateCharacterRequest(){
    CharacterRequest characterRequest = new CharacterRequest(EnumUtils.getRandomEnumValue(Type.class), faker.pokemon().name(), faker.number().numberBetween(1, 10), faker.number().numberBetween(1, 10), faker.number().numberBetween(1, 10), faker.number().numberBetween(1, 10), faker.number().numberBetween(1, 3) + "d" + faker.number().numberBetween(1, 12));
    return characterRequest;
  }

  public Character generateCharacter(){
    return new Character(null, EnumUtils.getRandomEnumValue(Type.class), faker.pokemon().name(), faker.number().numberBetween(1, 10), faker.number().numberBetween(1, 10), faker.number().numberBetween(1, 10), faker.number().numberBetween(1, 10), generateDice());
  }

  public Dice generateDice(){
    return new Dice(faker.number().numberBetween(1, 12), faker.number().numberBetween(1, 13));
  }

  public List<Character> generateCharacters(){
    List<Character> characterList = new ArrayList<Character>();
    for(int i = 0; i < 10; i++){
      characterList.add(generateCharacter());
    }
    return characterList;
  }

  public Battle generateBattle(Boolean withoutCharacter){
      Player playerOne = generatePlayer(withoutCharacter);
      Player playerTwo = generatePlayer(withoutCharacter);
      Battle battle = new Battle( );
      PlayerBattle playerBattleOne = new PlayerBattle();
      playerBattleOne.setBattle(battle);
      playerBattleOne.setPlayer(playerOne);
      PlayerBattle playerBattleTwo = new PlayerBattle();
      playerBattleTwo.setBattle(battle);
      playerBattleTwo.setPlayer(playerTwo);
      battle.setPlayers(Arrays.asList(playerBattleOne, playerBattleTwo));

      return battle;
  }

  public Battle generateBattle(){
    return generateBattle(false);
  }

  public Player generatePlayer(){
    return new Player(faker.name().username(), null, generateCharacter());
  }

  public Player generatePlayer(Boolean withoutCharacter){
    if(withoutCharacter){
      return new Player(faker.name().username(), null, null);
    }
    return generatePlayer();
  }

  public BattleCreateRequest generateBattleCreateRequest(){
    return new BattleCreateRequest(faker.name().username(), faker.name().username());
  }
}
