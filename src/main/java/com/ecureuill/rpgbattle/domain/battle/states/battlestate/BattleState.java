package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import com.ecureuill.rpgbattle.domain.battle.Battle;

public interface BattleState {
  void setNextState(Battle context);
}
