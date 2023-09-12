package com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy;

import java.util.List;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.BattleState;

public interface BattleNotCreatedStrategy extends BattleState{
  void createBattle(Battle context, List<Player> players);
}
