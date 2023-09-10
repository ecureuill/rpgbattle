package com.ecureuill.rpgbattle.domain.battle.state;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.Turn;

@ExtendWith(MockitoExtension.class)
public class AttackMoveStateTest {
  @Mock 
  private Turn context;
  @Mock
  private Player attackPlayer;
  @Mock
  private Player defensePlayer;
  @InjectMocks
  private AttackMoveState attackMoveState;

  @Test
  @DisplayName("Should set the correct next state")
  public void testHandle() {
    AttackMoveState attackMoveState = new AttackMoveState();
    attackMoveState.handle(context, attackPlayer, defensePlayer);
    Mockito.verify(context, Mockito.times(1)).setState(Mockito.any(DefenseMoveState.class));
  }
}
