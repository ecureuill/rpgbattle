package com.ecureuill.rpgbattle.domain.character;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CharacterTest {
  @DisplayName("Should set faces and quantity of dice")
  @Test
  public void shouldSetFacesAndQuantityOfDice(){
    Random random = new Random();
    Integer face = random.nextInt();
    Integer quantity = random.nextInt();
    Character character = new Character();
    character.setDice(quantity+"d"+face);
    Assertions.assertEquals(face, character.getDice().getFaces());
    Assertions.assertEquals(quantity, character.getDice().getQuantity());

  }
}
