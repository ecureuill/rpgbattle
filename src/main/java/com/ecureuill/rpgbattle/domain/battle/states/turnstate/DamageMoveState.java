package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.TurnDamageStrategy;
import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DamageMoveState implements TurnDamageStrategy {
  private TurnState next = new EndTurnState();

  @Override
  public void handle(Turn context, Player attackPlayer, Player defensePlayer) {
    SelectedCharacter character = attackPlayer.getCharacter();
    Dice dice = character.getDice();
    int damageDiceValue = dice.roll();
    int damage = character.producesDemage(damageDiceValue);
    defensePlayer.getCharacter().receiveDemage(damage);
    context.setDamageDiceValue(damageDiceValue);
    context.setDamage(damage);
  }

  @Override
  public void setNextState(Turn context) {
    context.setState(next);
  }
}
