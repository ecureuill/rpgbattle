package com.ecureuill.rpgbattle.application.exceptions;

import java.util.UUID;

public class TurnNotFoundException extends Exception {
public TurnNotFoundException(UUID turnId) {
  super("Turn with id " + turnId + " not found");
}
}
