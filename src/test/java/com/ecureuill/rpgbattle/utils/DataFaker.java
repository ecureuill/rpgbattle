package com.ecureuill.rpgbattle.utils;

import java.util.Locale;

import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.domain.character.Type;
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
}