package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import java.time.LocalDateTime;
import java.util.List;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy.BattleNotCreatedStrategy;

public class NotCreatedBattleState implements BattleNotCreatedStrategy {
  private final BattleState nextState = new CreatedBattleState();

  @Override
  public void setNextState(Battle context) {
    context.setState(nextState);
  }

  @Override
  public void createBattle(Battle context, List<Player> players) {
    context.getPlayers().get(0).setPlayer(players.get(0));
    context.getPlayers().get(1).setPlayer(players.get(1)); 
    context.setStartTime(LocalDateTime.now());
  }  
}
