package com.ecureuill.rpgbattle.domain.dice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DiceTest {

  @DisplayName("Should correctly set the number of faces and the roll times")
  @Test
  public void testDice_DefaultConstructor() {
    Dice dice = new Dice();
    Assertions.assertThat(dice.getFaces()).isEqualTo(12);
    Assertions.assertThat(dice.getRollTimes()).isEqualTo(1);
  }

  @DisplayName("Should correctly set the number of faces and the roll times")
  @Test
  public void testDice() {
    Dice dice = new Dice(6, 2);
    Assertions.assertThat(dice.getFaces()).isEqualTo(6);
    Assertions.assertThat(dice.getRollTimes()).isEqualTo(2);
  }

  @DisplayName("Should return a value beetween 1 and the number of faces")
  @Test
  public void testRoll_DefaultConstructor() {
    Dice dice = new Dice();
    int value = dice.roll();
    Assertions.assertThat(value).isBetween(1, 12);
  }

  @DisplayName("Should return a value beetween 1 and the number of face mutiplyed by the rollTimes")
  @Test
  public void testRoll() {
    Dice dice = new Dice(6, 2);
    int value = dice.roll();
    Assertions.assertThat(value).isBetween(1, 6*2);
  }
  
}
