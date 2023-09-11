package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;

public interface TurnState {
  void handle(Turn context, Player attackPlayer, Player defensePlayer);
}
