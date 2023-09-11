package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DefenseMoveState implements TurnState {
  @Override
  public void handle(Turn context, Player attackPlayer, Player defeensePlayer){
    context.setDefenceDiceValue(new Dice().roll());
    context.setState(new DemageMoveState());
  }

}
