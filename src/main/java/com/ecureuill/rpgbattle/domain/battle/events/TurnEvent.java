package com.ecureuill.rpgbattle.domain.battle.events;

import com.ecureuill.rpgbattle.domain.battle.Turn;

public interface TurnEvent {
  Turn getTurn();
}
