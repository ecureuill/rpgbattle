package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import com.ecureuill.rpgbattle.domain.battle.Battle;

public class EndBattleState implements BattleState {

  @Override
  public void setNextState(Battle context) {
    context.setState(null);
  }
  
}
