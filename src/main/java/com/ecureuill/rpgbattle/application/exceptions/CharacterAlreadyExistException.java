package com.ecureuill.rpgbattle.application.exceptions;

public class CharacterAlreadyExistException extends Exception{
  public CharacterAlreadyExistException(String specie) {
    super("Character " + specie + " already exist");
  }
}
