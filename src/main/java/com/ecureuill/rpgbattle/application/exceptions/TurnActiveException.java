package com.ecureuill.rpgbattle.application.exceptions;

import java.util.UUID;

public class TurnActiveException extends Exception {
  public TurnActiveException(UUID turnId) {
    super("A turn is already active!\nTurn id is " + turnId);
  }
}
