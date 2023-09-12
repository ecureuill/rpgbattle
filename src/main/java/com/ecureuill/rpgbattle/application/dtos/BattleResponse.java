package com.ecureuill.rpgbattle.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import com.ecureuill.rpgbattle.domain.battle.Battle;

public record BattleResponse(
  UUID battleId,
  PlayerResponse playerOne,
  PlayerResponse playerTwo,
  String stage,
  LocalDateTime startTime
) {
  public BattleResponse(Battle battle) {
    this(battle.getId(), new PlayerResponse(battle.getPlayers().get(0).getPlayer()), new PlayerResponse(battle.getPlayers().get(1).getPlayer()), battle.getStage().getName(), battle.getStartTime());
  }

}
