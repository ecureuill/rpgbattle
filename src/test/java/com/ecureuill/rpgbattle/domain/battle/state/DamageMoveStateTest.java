package com.ecureuill.rpgbattle.domain.battle.state;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.DamageMoveState;
import com.ecureuill.rpgbattle.domain.battle.states.turnstate.EndTurnState;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.utils.DataFaker;
import com.ecureuill.rpgbattle.utils.DataFakerProvider;

@ExtendWith(MockitoExtension.class)
public class DamageMoveStateTest {
  @Mock 
  private Turn context;
  @Mock
  private Player attackPlayer;
  @Mock
  private Player defensePlayer;
  @Mock
  private Character character;
  @InjectMocks
  private DamageMoveState damageMoveState;

  @Test
  @DisplayName("Should set the correct next state")
  public void testHandle(){
    DataFaker dataFaker = DataFakerProvider.getInstace();
    Mockito.when(attackPlayer.getCharacter()).thenReturn(new SelectedCharacter(dataFaker.generateCharacter()));
    Mockito.when(defensePlayer.getCharacter()).thenReturn(new SelectedCharacter(dataFaker.generateCharacter()));

    DamageMoveState damageMoveState = new DamageMoveState();
    damageMoveState.handle(context, attackPlayer, defensePlayer);
    Mockito.verify(context, Mockito.times(1)).setState(Mockito.any(EndTurnState.class));

  }
}
