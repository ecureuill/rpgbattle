package com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy;

import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnState;
import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;

public interface TurnAttackStrategy extends TurnState {
    void handle(Turn context, SelectedCharacter attackingPlayer);
}