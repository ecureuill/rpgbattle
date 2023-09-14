package com.ecureuill.rpgbattle.application.exceptions;

public class NotPlayerTurnException extends Exception{

  public NotPlayerTurnException(String username) {
    super("It is not " + username + " tun");
  }

}
