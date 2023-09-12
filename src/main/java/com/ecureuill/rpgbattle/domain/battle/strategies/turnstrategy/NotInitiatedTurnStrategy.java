package com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnState;

public interface NotInitiatedTurnStrategy extends TurnState {
  public Turn startTurn(Battle context);
}
