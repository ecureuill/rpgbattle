package com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.BattleState;
import com.ecureuill.rpgbattle.domain.character.Character;

public interface BattleCreatedStrategy extends BattleState {
  void addCharacter(Battle context, String username, Character character);  
}
