package com.ecureuill.rpgbattle.application.exceptions;

public class AuthorizationException extends Exception {
  public AuthorizationException(String username) {
    super("User " + username + " is not authorized to perform this operation");
  }
  
}
