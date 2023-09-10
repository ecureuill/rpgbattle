package com.ecureuill.rpgbattle.domain.battle.strategies;

import java.time.LocalDateTime;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.events.TurnEvent;

public class AttackTurnEventStrategy implements TurnEventStrategy {
  @Override
  public void handleEvent(TurnEvent event, Battle battle){
    battle.setCurrentTurn(event.getTurn().getId());
    battle.setLastTurnTime(LocalDateTime.now());
  }
}
  
