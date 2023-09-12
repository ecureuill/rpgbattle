package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.PlayerBattle;
import com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy.BattleNotCreatedStrategy;

public class NotCreatedBattleState implements BattleNotCreatedStrategy {
  private final BattleState nextState = new CreatedBattleState();

  @Override
  public void setNextState(Battle context) {
    context.setState(nextState);
  }

  @Override
  public void createBattle(Battle context, Player playerOne, Player playerTwo) {

    PlayerBattle playerBattleOne = new PlayerBattle();
    playerBattleOne.setBattle(context);
    playerBattleOne.setPlayer(playerOne);
    PlayerBattle playerBattleTwo = new PlayerBattle();
    playerBattleTwo.setBattle(context);
    playerBattleTwo.setPlayer(playerTwo);
    context.setPlayers(Arrays.asList(playerBattleOne, playerBattleTwo));
    playerOne.setBattles(Arrays.asList(playerBattleOne));
    playerTwo.setBattles(Arrays.asList(playerBattleTwo));
    context.setStartTime(LocalDateTime.now());
    context.setState(nextState);

  }  
}
