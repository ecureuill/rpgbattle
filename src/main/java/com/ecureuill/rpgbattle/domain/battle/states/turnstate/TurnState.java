package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Turn;

public interface TurnState {
  void setNextState(Turn context);
}
