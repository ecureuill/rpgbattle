package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Turn;

public class EndTurnState implements TurnState {
  private TurnState next = null;
  @Override
  public void setNextState(Turn context) {
    context.setState(next);
  }
}
