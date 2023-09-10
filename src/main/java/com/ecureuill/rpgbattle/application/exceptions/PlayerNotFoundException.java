package com.ecureuill.rpgbattle.application.exceptions;

public class PlayerNotFoundException extends Exception {
  public PlayerNotFoundException(String username) {
    super("Player " + username + " not found");
  }
}
