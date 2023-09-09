package com.ecureuill.rpgbattle.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.domain.character.Type;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.character.Dice;
import com.github.javafaker.Faker;

public class DataFaker {
  Faker faker; 

  public DataFaker() {
    faker = new Faker(new Locale("pt-BR"));
  }

  public CharacterRequest generaCharacterRequest(){
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
}
