package com.ecureuill.rpgbattle.infrastructure.providers;

import java.util.Random;

public final class RandomProvider {
  private static Random instance = new Random();
  private RandomProvider() {

  }
  public static Random getInstance() {
    return instance;
  }
}
