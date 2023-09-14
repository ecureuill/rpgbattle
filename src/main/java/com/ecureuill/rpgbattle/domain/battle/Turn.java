package com.ecureuill.rpgbattle.domain.battle;

import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
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
  private Integer attack;
  private Integer defenceDiceValue;
  private Integer defense;
  private Integer demageDiceValue;
  private Integer demage;
  @Transient 
  private TurnState state;
  @Enumerated(EnumType.STRING)
  private TurnStateType stateType;

  public Turn() {
    this.turnSequence = 0;
    this.state = new AttackMoveState();
  }

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

  public void setState(TurnState state) {
    this.state = state;
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
}
