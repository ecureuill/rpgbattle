package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Turn;

public interface TurnAttackStrategy extends TurnState {
    void handle(Turn context);
}