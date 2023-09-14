package com.ecureuill.rpgbattle.domain.battle.states.battlestate;

import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.strategies.battlestrategy.BattleInitiativeStrategy;

public class InitiativeBattleState implements BattleInitiativeStrategy {
  private final BattleState nexState = new TurnsBattleState();
  private final static int INITIATIVE_DICE_FACES = 20;

  @Override
  public void setNextState(Battle context) {
    if(context.getInitiative().getPlayerOneDiceValue().equals(context.getInitiative().getPlayerTwoDiceValue())) {
      return;
    }
    
    context.setState(nexState);
  }

  @Override
  public void determineInitiative(Battle context) {
    context.getDice().setFaces(INITIATIVE_DICE_FACES);
    
    context.getInitiative().setPlayerOneDiceValue(context.getDice().roll());
    context.getInitiative().setPlayerTwoDiceValue(context.getDice().roll());

    if(context.getInitiative().getPlayerOneDiceValue() > context.getInitiative().getPlayerTwoDiceValue()) {
      context.setPlayerTurn(0);
    }
    else if(context.getInitiative().getPlayerOneDiceValue() < context.getInitiative().getPlayerTwoDiceValue()) {
      context.setPlayerTurn(1);
    }
    setNextState(context);
  }


}
