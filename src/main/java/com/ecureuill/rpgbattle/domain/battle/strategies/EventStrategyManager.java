package com.ecureuill.rpgbattle.domain.battle.strategies;

import java.util.HashMap;
import java.util.Map;

import com.ecureuill.rpgbattle.domain.battle.events.AttackTurnEvent;
import com.ecureuill.rpgbattle.domain.battle.events.EndTurnEvent;
import com.ecureuill.rpgbattle.domain.battle.events.TurnEvent;

public class EventStrategyManager {
  private Map<Class<? extends TurnEvent>, TurnEventStrategy> strategies;

  public EventStrategyManager() {
    this.strategies = new HashMap<>();
    this.strategies.put(EndTurnEvent.class, new EndTurnEventStrategy());
    this.strategies.put(AttackTurnEvent.class, new AttackTurnEventStrategy());
  }

  public TurnEventStrategy getStrategy(TurnEvent turnEvent) {
    return strategies.get(turnEvent.getClass());
  }
 
}
