package com.ecureuill.rpgbattle.domain.battle.strategies;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.events.TurnEvent;

public interface TurnEventStrategy {
  void handleEvent(TurnEvent event, Battle battle);
}
