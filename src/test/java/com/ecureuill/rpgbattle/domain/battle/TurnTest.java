package com.ecureuill.rpgbattle.domain.battle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.domain.battle.state.AttackMoveState;
import com.ecureuill.rpgbattle.domain.battle.state.DefenseMoveState;
import com.ecureuill.rpgbattle.domain.battle.state.DemageMoveState;
import com.ecureuill.rpgbattle.domain.battle.state.EndTurnState;
import com.ecureuill.rpgbattle.domain.battle.state.TurnState;

@ExtendWith(MockitoExtension.class)
public class TurnTest {
  @Mock
  private Player attackPlayer;
  @Mock
  private Player defensePlayer;
  @InjectMocks
  private Turn turn;

  @DisplayName("Should call the correct handle method do AttackMoveState")
  @Test
  public void testMovement_AttackState() {
    TurnState state = new AttackMoveState();
    turn.setState(state);
    turn.movement(attackPlayer, defensePlayer);
    Mockito.verify(state, Mockito.times(1)).handle(turn, attackPlayer, defensePlayer);
  }

  @DisplayName("Should call the correct handle method do DefenseMoveState")
  @Test
  public void testMovement_DefenseState() {
    TurnState state = new DefenseMoveState();
    turn.setState(state);
    turn.movement(attackPlayer, defensePlayer);
    Mockito.verify(state, Mockito.times(1)).handle(turn, attackPlayer, defensePlayer);
  }

  @DisplayName("Should call the correct handle method do EndTurnState")
  @Test
  public void testMovement_EndTurnState() {
    TurnState state = new EndTurnState();
    turn.setState(state);
    turn.movement(attackPlayer, defensePlayer);
    Mockito.verify(state, Mockito.times(1)).handle(turn, attackPlayer, defensePlayer);
  }

  @DisplayName("Should call the correct handle method do DemageMoveState")
  @Test
  public void testMovement_DemageMoveState() {
    TurnState state = new DemageMoveState();
    turn.setState(state);
    turn.movement(attackPlayer, defensePlayer);
    Mockito.verify(state, Mockito.times(1)).handle(turn, attackPlayer, defensePlayer);
  }
}
