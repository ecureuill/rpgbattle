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
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DefenseMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DemageMoveState;

@ExtendWith(MockitoExtension.class)
public class DefenseMoveStateTest {
  @Mock 
  private Turn context;
  @Mock
  private Player attackPlayer;
  @Mock
  private Player defensePlayer;
  @InjectMocks
  private DefenseMoveState defenseMoveState;

  @DisplayName("Should set the correct next state")
  @Test
  void testHandle() {
    DefenseMoveState defenseMoveState = new DefenseMoveState();
    defenseMoveState.handle(context);
    Mockito.verify(context, Mockito.times(1)).setState(Mockito.any(DemageMoveState.class));
  }
}
