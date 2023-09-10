package com.ecureuill.rpgbattle.domain.battle.events;

import org.springframework.context.ApplicationEvent;

import com.ecureuill.rpgbattle.domain.battle.Turn;

public class AttackTurnEvent extends ApplicationEvent implements TurnEvent {
  private Turn turn;
  
  public AttackTurnEvent(Object source, Turn turn) {
    super(source);
    this.turn = turn;
  }

  @Override
  public Turn getTurn() {
    return turn;
  }

}
