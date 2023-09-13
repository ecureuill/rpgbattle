package com.ecureuill.rpgbattle.application.dtos;

import com.ecureuill.rpgbattle.domain.battle.Initiative;

public record InitiativeResponse(
  String status,
  Integer playerOneDiceValue,
  Integer playerTwoDiceValue
) {
  public InitiativeResponse(String status, Integer playerOneDiceValue, Integer playerTwoDiceValue){
    this.status = status;
    this.playerOneDiceValue = playerOneDiceValue;
    this.playerTwoDiceValue = playerTwoDiceValue;
  }

  public InitiativeResponse(Initiative initiative) {
    this(initiative.getStatus(), initiative.getPlayerOneDiceValue(), initiative.getPlayerTwoDiceValue());
  }
}
