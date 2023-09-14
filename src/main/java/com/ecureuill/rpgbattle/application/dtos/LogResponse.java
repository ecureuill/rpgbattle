package com.ecureuill.rpgbattle.application.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.ecureuill.rpgbattle.domain.battle.Battle;

public record LogResponse(
  UUID battleId,
  LocalDateTime startTime,
  LocalDateTime endTime,
  LocalDateTime lastTurnTime,
  String stage,
  InitiativeResponse initiative,
  PlayerResponse playerOne,
  PlayerResponse playerTwo,
  List<TurnResponse> turns
) {
  public static LogResponse of(Battle battle) {
    return new LogResponse(
        battle.getId(),
        battle.getStartTime(),
        battle.getEndTime(),
        battle.getLastTurnTime(),
        battle.getStage().getName(),
        new InitiativeResponse(battle.getInitiative()),
        new PlayerResponse(battle.getPlayers().get(0).getPlayer()),
        new PlayerResponse(battle.getPlayers().get(1).getPlayer()),
        battle.getTurns().stream().map(TurnResponse::new).collect(Collectors.toList())
    );
  }
}
