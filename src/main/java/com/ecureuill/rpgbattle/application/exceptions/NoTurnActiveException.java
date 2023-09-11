package com.ecureuill.rpgbattle.application.exceptions;

public class NoTurnActiveException extends Exception {
  public NoTurnActiveException() {
    super("Start a turn before perform this action");  
  }
}
