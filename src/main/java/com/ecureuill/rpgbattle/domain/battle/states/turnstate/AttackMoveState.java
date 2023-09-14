package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.TurnAttackStrategy;
import com.ecureuill.rpgbattle.domain.dice.Dice;
import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;

public class AttackMoveState implements TurnAttackStrategy{
  private TurnState next = new DefenseMoveState();

  @Override
  public void setNextState(Turn context) {
    context.setState(next);
  }
  
  @Override
  public void handle(Turn context, SelectedCharacter character) {
    Integer diceValue = (new Dice()).roll();
    context.setAttackDiceValue(diceValue);
    context.setAttack(character.calculateAttack(diceValue));
    context.setState(next);
  }
}