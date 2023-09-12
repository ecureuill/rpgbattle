package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.TurnDefenseStrategy;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DefenseMoveState implements TurnDefenseStrategy {
  private TurnState nextDemage = new DemageMoveState();
  private TurnState nextEnd = new EndTurnState();

  @Override
  public void handle(Turn context) {
    context.setDefenceDiceValue(new Dice().roll());
  }

  @Override
  public void setNextState(Turn context) {
    if(context.getAttackDiceValue() > context.getDefenceDiceValue()){
      context.setState(nextDemage);
    }
    else {
      context.setState(nextEnd);
    }
  }

}
