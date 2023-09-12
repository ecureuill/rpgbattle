package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DefenseMoveState implements TurnDefenseStrategy {
  private TurnState next = new DemageMoveState();
  
  @Override
  public void handle(Turn context) {
    context.setDefenceDiceValue(new Dice().roll());
  }

  @Override
  public void setNextState(Turn context) {
    context.setState(next);
  }

}
