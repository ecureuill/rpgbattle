package com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy;

import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnState;

public interface TurnDefenseStrategy extends TurnState{
    void handle(Turn context, SelectedCharacter defendingPlayer);
}
