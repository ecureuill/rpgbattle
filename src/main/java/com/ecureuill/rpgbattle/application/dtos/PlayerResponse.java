package com.ecureuill.rpgbattle.application.dtos;

import com.ecureuill.rpgbattle.domain.battle.Player;

public record PlayerResponse(
  String username, 
  SelectedCharacterResponse character
) {

  public PlayerResponse(Player player) {
    this(player.getUsername(), player.getCharacter() == null? null : new SelectedCharacterResponse(player.getCharacter()));
  }  
}
