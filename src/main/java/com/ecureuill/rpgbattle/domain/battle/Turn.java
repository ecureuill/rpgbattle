package com.ecureuill.rpgbattle.domain.battle;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.ecureuill.rpgbattle.domain.battle.events.AttackTurnEvent;
import com.ecureuill.rpgbattle.domain.battle.events.EndTurnEvent;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.AttackMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DefenseMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DemageMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.EndTurnState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.TurnStateType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "turns")
@Data
public class Turn implements ApplicationEventPublisherAware {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private Integer turnSequence;
  private Integer attackDiceValue;
  private Integer defenceDiceValue;
  private Integer demageDiceValue;
  private Integer demage;
  //Is not possible to mapped interface, so it is annoted as @Transient and an Enum is used as an conversion strategy
  @Transient 
  private TurnState state;
  @Enumerated(EnumType.STRING)
  private TurnStateType stateType;
  @Transient
  private ApplicationEventPublisher eventPublisher;

  @PostLoad
  void fillTransient() {
    if(stateType != null){
      switch (stateType) {
        case IS_ATACK_MOVE:
          state = new AttackMoveState();
          break;
        case IS_DEFENSE_MOVE:
          state = new DefenseMoveState();
          break;
        case IS_DEMAGE_MOVE:
          state = new DemageMoveState();
          break;
        case IS_END:
          state = new EndTurnState();
          break;
        default:
          break;
      }
    }
  }

  @PrePersist
  void fillPersist() {
    if(state instanceof EndTurnState) {
      this.stateType = TurnStateType.IS_END;
    } else if (state instanceof AttackMoveState) {
      this.stateType = TurnStateType.IS_ATACK_MOVE;
    } else if (state instanceof DefenseMoveState) {
      this.stateType = TurnStateType.IS_DEFENSE_MOVE;
    } else if (state instanceof DemageMoveState) {
      this.stateType = TurnStateType.IS_DEMAGE_MOVE;
    }
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.eventPublisher = applicationEventPublisher;
  }

  public void movement(Player attackPlayer, Player defensePlayer){

    if(state instanceof AttackMoveState){
      ((AttackMoveState)state).handle(this);
    }
    else if(state instanceof DefenseMoveState){
      ((DefenseMoveState)state).handle(this);
    }
    else if(state instanceof DemageMoveState){
      ((DemageMoveState)state).handle(this, attackPlayer, defensePlayer);
    } 
    else {
      return;
    }
    state.setNextState(null);

    if(state instanceof EndTurnEvent) {
      eventPublisher.publishEvent(new EndTurnEvent(this, this));
    }

    if(state instanceof AttackMoveState) {
      eventPublisher.publishEvent(new AttackTurnEvent(this, this));
    }
  }

}

