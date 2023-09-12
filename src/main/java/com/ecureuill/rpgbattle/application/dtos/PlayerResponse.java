package com.ecureuill.rpgbattle.application.dtos;

import com.ecureuill.rpgbattle.domain.battle.Player;

public record PlayerResponse(
  String username, 
  CharacterResponse character
) {

  public PlayerResponse(Player player) {
    this(player.getUsername(), player.getCharacter() == null? null : new CharacterResponse(player.getCharacter()));
  }  
}
