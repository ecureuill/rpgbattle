package com.ecureuill.rpgbattle.domain.battle.state;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;

public interface TurnState {
  void handle(Turn context, Player attackPlayer, Player defensePlayer);
}
