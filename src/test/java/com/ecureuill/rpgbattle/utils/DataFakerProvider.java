package com.ecureuill.rpgbattle.utils;

public final class DataFakerProvider {
  private static final DataFaker INSTANCE = new DataFaker();

  private DataFakerProvider() {
  }

  public static DataFaker getInstace() {
    return INSTANCE;
  }
}
