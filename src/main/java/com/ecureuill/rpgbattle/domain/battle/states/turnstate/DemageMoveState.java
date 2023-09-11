package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public class DemageMoveState implements TurnDemageStrategy {
  private TurnState nextState = new EndTurnState();

  @Override
  public void handle(Turn context, Player attackPlayer, Player defensePlayer) {
    Character character = attackPlayer.getCharacter();
    Dice dice = character.getDice();
    int demageDiceValue = dice.roll();
    int demage = character.producesDemage(demageDiceValue);
    defensePlayer.getCharacter().receiveDemage(demage);
    context.setDemageDiceValue(demageDiceValue);
    context.setDemage(demage);
    context.setState(nextState);
  }
}
