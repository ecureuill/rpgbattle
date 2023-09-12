package com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnState;

public interface TurnDemageStrategy extends TurnState {
  public void handle(Turn context, Player attackPlayer, Player defensePlayer);
}
