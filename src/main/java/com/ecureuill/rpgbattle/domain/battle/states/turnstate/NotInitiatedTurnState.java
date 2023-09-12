package com.ecureuill.rpgbattle.domain.battle.states.turnstate;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.strategies.turnstrategy.NotInitiatedTurnStrategy;

public class NotInitiatedTurnState implements NotInitiatedTurnStrategy {
  private TurnState next = new AttackMoveState();

  @Override
  public void setNextState(Turn context) {
    context.setState(next);
  }

  @Override
  public Turn startTurn(Battle battle) {
    Turn turn = new Turn();

    //first battle turn
    if(battle.getTurnsSequence() == 0){
      OptionalInt turnIndex = IntStream.range(0, battle.getPlayers().size()).filter(index -> battle.getPlayers().get(index).getPlayer().getUsername().equals(battle.getInitiative().getPlayer().getUsername())).findFirst();

      battle.setPlayerTurn(turnIndex.getAsInt());
    }
    else {
      battle.setPlayerTurn((battle.getPlayerTurn()+1) * battle.getPlayers().size());
    }
    
    turn.setState(next);
    return turn;
  }
}