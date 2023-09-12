package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import com.ecureuill.rpgbattle.application.exceptions.NoTurnActiveException;
import com.ecureuill.rpgbattle.application.exceptions.TurnActiveException;
import com.ecureuill.rpgbattle.application.exceptions.TurnNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.PlayerBattle;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.events.EndTurnEvent;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.AttackMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DefenseMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DemageMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.NotInitiatedTurnState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnState;
import com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy.BattleTurnsStrategy;

public class TurnsBattleState implements BattleTurnsStrategy {
  private final BattleState nextState = new TurnsBattleState();
  private TurnState state;

  public void setNextTurnState(TurnState state){
    this.state = state;
  }

  public TurnState getTurnState(){
    return state;
  }
  @Override
  public void setNextState(Battle context) {
    context.setState(nextState);
  }

  @Override
  public void startTurn(Battle context) throws TurnActiveException {
    if(context.getCurrentTurn() == null) {
      this.state = new AttackMoveState();
    }  
    else {
      throw new TurnActiveException(context.getCurrentTurn());
    }
  }

  @Override
  public void turnAction(Battle context) throws NoTurnActiveException, TurnNotFoundException {
    if(context.getCurrentTurn() == null) {
      throw new NoTurnActiveException();
    }

    Turn turn = getCurrentTurn(context);
    TurnState state = turn.getState();

    if(state instanceof NotInitiatedTurnState){
      throw new NoTurnActiveException();
    }
    else if(state instanceof AttackMoveState){
      AttackMoveState turnState = (AttackMoveState) state;
      turnState.handle(turn);
      turnState.setNextState(turn);
    }
    else if(state instanceof DefenseMoveState){
      DefenseMoveState turnState = (DefenseMoveState) state;
      turnState.handle(turn);
      turnState.setNextState(turn);
    }      
    else if(state instanceof DemageMoveState) {
      DemageMoveState turnState = (DemageMoveState) state;
      //TO-DO pegar os players corretos
      turnState.handle(turn, context.getPlayers().get(0).getPlayer(), context.getPlayers().get(1).getPlayer());
      turnState.setNextState(turn);
    }
    else if(state instanceof EndTurnEvent) {
      context.setCurrentTurn(null);
      if(context.getPlayers().stream().map(PlayerBattle::getPlayer).anyMatch(player -> player.getCharacter().getLife() <= 0)){
        setNextState(context);
      }
    }
  }

  private Turn getCurrentTurn(Battle context) throws TurnNotFoundException {
    return context.getTurns().stream().filter(item -> item.getId().equals(context.getCurrentTurn())).findFirst().orElseThrow(() -> new TurnNotFoundException(context.getCurrentTurn()));
  }
}