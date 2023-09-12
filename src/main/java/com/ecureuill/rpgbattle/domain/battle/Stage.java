package com.ecureuill.rpgbattle.domain.battle;

public enum Stage {
  NOT_CREATED("Battle not created"),
  CHARACTER_SELECTION("Character selection phase"),
  INITIATIVE("Initiative phase"), 
  TURNS("Turn phase"), 
  END("Battle finished");

  private final String name;
  Stage(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
}
