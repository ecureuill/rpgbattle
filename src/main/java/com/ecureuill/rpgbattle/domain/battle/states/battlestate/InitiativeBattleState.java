package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy.BattleInitiativeStrategy;

public class InitiativeBattleState implements BattleInitiativeStrategy {
  private final BattleState nexState = new TurnsBattleState();
  private final static int INITIATIVE_DICE_FACES = 20;

  @Override
  public void setNextState(Battle context) {
    if(context.getInitiativa().getPlayerOneDiceValue().equals(context.getInitiativa().getPlayerTwoDiceValue())) {
      return;
    }
      
    context.setState(nexState);
  }

  @Override
  public void determineInitiative(Battle context) {
    context.getDice().setFaces(INITIATIVE_DICE_FACES);
    
    context.getInitiativa().setPlayerOneDiceValue(context.getDice().roll());
    context.getInitiativa().setPlayerTwoDiceValue(context.getDice().roll());

    if(context.getInitiativa().getPlayerOneDiceValue() > context.getInitiativa().getPlayerTwoDiceValue()) {
      context.getInitiativa().setPlayer(context.getPlayers().get(0).getPlayer());
    }
    else if(context.getInitiativa().getPlayerOneDiceValue() < context.getInitiativa().getPlayerTwoDiceValue()) {
      context.getInitiativa().setPlayer(context.getPlayers().get(1).getPlayer());
    }
    else {
      context.getInitiativa().setPlayer(null);
    }
  }
}
