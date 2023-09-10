package com.ecureuill.rpgbattle.domain.battle;

import java.util.UUID;

import com.ecureuill.rpgbattle.domain.battle.state.AttackMoveState;
import com.ecureuill.rpgbattle.domain.battle.state.DefenseMoveState;
import com.ecureuill.rpgbattle.domain.battle.state.DemageMoveState;
import com.ecureuill.rpgbattle.domain.battle.state.EndTurnState;
import com.ecureuill.rpgbattle.domain.battle.state.TurnState;
import com.ecureuill.rpgbattle.domain.battle.state.TurnStateType;

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
public class Turn {
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

  public void movement(Player attackPlayer, Player defensePlayer){
    this.state.handle(this, attackPlayer, defensePlayer);
  }
}

