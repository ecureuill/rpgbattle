package com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy;

import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.BattleState;

public interface BattleCreatedStrategy extends BattleState {
  void addCharacter(Battle context, String username, Character character) throws PlayerNotFoundException;  
}
