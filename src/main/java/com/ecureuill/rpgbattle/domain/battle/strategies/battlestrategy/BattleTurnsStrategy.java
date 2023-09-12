package com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy;

import com.ecureuill.rpgbattle.application.exceptions.NoTurnActiveException;
import com.ecureuill.rpgbattle.application.exceptions.TurnActiveException;
import com.ecureuill.rpgbattle.application.exceptions.TurnNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.BattleState;

public interface BattleTurnsStrategy extends BattleState {
  void startTurn(Battle context) throws TurnActiveException;
  void turnAction(Battle context) throws NoTurnActiveException, TurnNotFoundException;
}
