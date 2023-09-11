package com.ecureuill.rpgbattle.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ecureuill.rpgbattle.domain.battle.Battle;

public record BattleResponse(
  UUID battleId,
  String playerOne,
  String playerTwo,
  String stage,
  LocalDateTime startTime
) {
  public BattleResponse(Battle battle) {
    this(battle.getId(), battle.getPlayers().get(0).getPlayer().getUsername(), battle.getPlayers().get(1).getPlayer().getUsername(), battle.getStage().getName(), battle.getStartTime());
  }

}
