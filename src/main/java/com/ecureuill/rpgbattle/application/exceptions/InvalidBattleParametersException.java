package com.ecureuill.rpgbattle.application.exceptions;

public class InvalidBattleParametersException extends Exception {
  public InvalidBattleParametersException(String message) {
    super(message);
  }

  public InvalidBattleParametersException(String message, Throwable initCause) {
    super(message, initCause);
  }
}
