package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.PlayerBattle;
import com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy.BattleCreatedStrategy;
import com.ecureuill.rpgbattle.domain.character.Character;


public class CreatedBattleState implements BattleCreatedStrategy {
  private final BattleState nexState = new InitiativeBattleState();

  @Override
  public void setNextState(Battle context) {
    if (context.getPlayers().stream().map(PlayerBattle::getPlayer).anyMatch(player -> player.getCharacter() == null)) {
      return;
    }
    context.setState(nexState);
  }

  @Override
  public void addCharacter(Battle context, String username, Character character) {
    Player player = findPlayerByUsername(context, username);
    player.setCharacter(character);
  }

  private Player findPlayerByUsername(Battle context, String username) {
    return context.getPlayers().stream().map(PlayerBattle::getPlayer)
        .filter(player -> player.getUsername().equals(username))
        .findFirst()
        .orElseThrow();
  }
}
