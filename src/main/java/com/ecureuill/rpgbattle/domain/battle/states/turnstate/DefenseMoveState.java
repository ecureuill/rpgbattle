package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.TurnDefenseStrategy;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DefenseMoveState implements TurnDefenseStrategy {
  private TurnState nextDemage = new DemageMoveState();
  private TurnState nextEnd = new EndTurnState();

  @Override
  public void handle(Turn context, SelectedCharacter character) {
    Integer diceValue = (new Dice()).roll();
    context.setDefenceDiceValue(diceValue);
    context.setDefense(character.calculateDenfense(diceValue));
    setNextState(context);
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
