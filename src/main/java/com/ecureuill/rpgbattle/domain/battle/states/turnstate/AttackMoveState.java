package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class AttackMoveState implements TurnState{
  @Override
  public void handle(Turn context, Player attackPlayer, Player defensePlayer) {
    context.setAttackDiceValue(new Dice().roll());
    context.setState(new DefenseMoveState());
  }    
}
