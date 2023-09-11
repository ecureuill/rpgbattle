package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class AttackMoveState implements TurnAttackStrategy{
  private TurnState nextState = new DefenseMoveState();

  @Override
  public void handle(Turn context) {
    context.setAttackDiceValue(new Dice().roll());
    context.setState(nextState);
  }

  @Override
  public void handle(Turn context, Player attackPlayer, Player defensePlayer) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }
}