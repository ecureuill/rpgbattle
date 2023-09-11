package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;

public class EndTurnState implements TurnState {
  @Override
  public void handle(Turn context, Player attackPlayer, Player defensePlayer) {
    //DO NOTHING
  }
}
