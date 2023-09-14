package com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy;

import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.states.battlestate.BattleState;

public interface BattleCreatedStrategy extends BattleState {
  void addCharacter(Battle context, String username, SelectedCharacter character) throws PlayerNotFoundException;  
}
