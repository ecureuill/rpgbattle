package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.TurnDefenseStrategy;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DefenseMoveState implements TurnDefenseStrategy {
  private TurnState nextDamage = new DamageMoveState();
  private TurnState nextEnd = new EndTurnState();

  @Override
  public void handle(Turn context, SelectedCharacter character) {
    Integer diceValue = (new Dice()).roll();
    context.setDefenseDiceValue(diceValue);
    context.setDefense(character.calculateDenfense(diceValue));
    setNextState(context);
  }

  @Override
  public void setNextState(Turn context) {
    if(context.getAttackDiceValue() > context.getDefenseDiceValue()){
      context.setState(nextDamage);
    }
    else {
      context.setState(nextEnd);
    }
  }

}
