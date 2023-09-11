package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DefenseMoveState implements TurnDefenseStrategy {
  private TurnState nextState = new DemageMoveState();
  
  @Override
  public void handle(Turn context, Player attackPlayer, Player defeensePlayer){
      throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }   


  @Override
  public void handle(Turn context) {
    context.setDefenceDiceValue(new Dice().roll());
    context.setState(nextState);
  }

}
