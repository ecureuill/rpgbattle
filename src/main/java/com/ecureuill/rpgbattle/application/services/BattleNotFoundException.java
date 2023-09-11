package com.ecureuill.rpgbattle.application.services;

import java.util.UUID;

public class BattleNotFoundException extends Exception {
  public BattleNotFoundException(UUID uuid) {
    super("Battle with id " + uuid + " not found");
  }
}
