package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import java.util.Optional;
import com.ecureuill.rpgbattle.application.exceptions.NoTurnActiveException;
import com.ecureuill.rpgbattle.application.exceptions.TurnNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.PlayerBattle;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.AttackMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DefenseMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DemageMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.EndTurnState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.NotInitiatedTurnState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnState;
import com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy.BattleTurnsStrategy;

public class TurnsBattleState implements BattleTurnsStrategy {
  private final BattleState nextState = new EndBattleState();
  private TurnState state;

  public TurnState getTurnState(){
    return state;
  }
  
  @Override
  public void setNextState(Battle context) {
    context.setState(nextState);
  }

  @Override
  public void turnAction(Battle context) throws NoTurnActiveException, TurnNotFoundException {
    if(context.getCurrentTurnId() == null) {
      throw new NoTurnActiveException();
    }

    Optional<Turn> optionalTurn = context.getCurrentTurn();
    if(optionalTurn.isEmpty())
    {
      throw new TurnNotFoundException(context.getCurrentTurnId());
    }
    
    Turn turn = optionalTurn.get();
    state = turn.getState();

    if(state instanceof NotInitiatedTurnState){
      throw new NoTurnActiveException();
    }
    else if(state instanceof AttackMoveState){
      AttackMoveState turnState = (AttackMoveState)state;
      turnState.handle(turn, getAttackingPlayer(context).getCharacter());
      context.getTurns().stream().map(item -> item.getId().equals(turn.getId())? turn: item);
    }
    else if(state instanceof DefenseMoveState){
      DefenseMoveState turnState = (DefenseMoveState)state;
      turnState.handle(turn, getDefendingPlayer(context).getCharacter());

    }      
    
    if(state instanceof DemageMoveState) {
      DemageMoveState turnState = (DemageMoveState)state;
      turnState.handle(turn, getAttackingPlayer(context), getDefendingPlayer(context));
      turnState.setNextState(turn);
      state = turn.getState();
    }

    if(turn.getState() instanceof EndTurnState) {
      context.setPlayerTurn((!Boolean.valueOf(context.getPlayerTurn().toString())? 0 : 1));

      if(context.getPlayers().stream().map(PlayerBattle::getPlayer).anyMatch(player -> player.getCharacter().getLife() <= 0)){
        setNextState(context);
      }
    }
  }

  private Player getAttackingPlayer(Battle context){
    return context.getPlayers().get(context.getPlayerTurn()).getPlayer();
  }

  private Player getDefendingPlayer(Battle context){
    return context.getPlayers().get(context.getPlayerTurn() == 0? 1 : 0).getPlayer();
  }
}