package com.ecureuill.rpgbattle.domain.battle.events;

import org.springframework.context.ApplicationEvent;

import com.ecureuill.rpgbattle.domain.battle.Turn;

public class EndTurnEvent extends ApplicationEvent implements TurnEvent {
  private Turn turn;

  public EndTurnEvent(Object source, Turn turn) {
    super(source);
    this.turn = turn;
  }

  @Override
  public Turn getTurn() {
    return turn;
  }
  
}
