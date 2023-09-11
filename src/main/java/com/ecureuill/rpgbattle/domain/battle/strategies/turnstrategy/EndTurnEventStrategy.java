package com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy;

import java.time.LocalDateTime;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.PlayerBattle;
import com.ecureuill.rpgbattle.domain.battle.Stage;
import com.ecureuill.rpgbattle.domain.battle.events.TurnEvent;

public class EndTurnEventStrategy implements TurnEventStrategy {
  @Override
  public void handleEvent(TurnEvent event, Battle battle){
    battle.setCurrentTurn(null);
    battle.setEndTime(LocalDateTime.now());

    if(battle.getPlayers().stream().map(PlayerBattle::getPlayer).anyMatch(player -> player.getCharacter().getLife() <= 0)){
      battle.setStage(Stage.END);
    }

  }
  
}
