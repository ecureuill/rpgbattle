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
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.dice.Dice;
import com.ecureuill.rpgbattle.utils.DataFaker;

@ExtendWith(MockitoExtension.class)
public class DemageMoveStateTest {
  @Mock 
  private Turn context;
  @Mock
  private Player attackPlayer;
  @Mock
  private Player defensePlayer;
  @Mock
  private Character character;
  @InjectMocks
  private DemageMoveState demageMoveState;

  @Test
  @DisplayName("Should set the correct next state")
  public void testHandle(){
    DataFaker dataFaker = new DataFaker();
    Mockito.when(attackPlayer.getCharacter()).thenReturn(dataFaker.generateCharacter());
    Mockito.when(defensePlayer.getCharacter()).thenReturn(dataFaker.generateCharacter());

    DemageMoveState demageMoveState = new DemageMoveState();
    demageMoveState.handle(context, attackPlayer, defensePlayer);
    Mockito.verify(context, Mockito.times(1)).setState(Mockito.any(EndTurnState.class));

  }
}
