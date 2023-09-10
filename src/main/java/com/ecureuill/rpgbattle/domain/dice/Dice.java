package com.ecureuill.rpgbattle.domain.dice;

import java.util.Random;

import com.ecureuill.rpgbattle.infrastructure.providers.RandomProvider;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Dice {
  private static final int NUMBER_OF_DICE_FACES = 12;
  private static final int MIN_DICE_FACE = 1;

  private Integer faces;
  private Integer rollTimes;

  public Dice() {
    this.faces = NUMBER_OF_DICE_FACES;
    this.rollTimes = 1;
  }

  public Dice(Integer faces, Integer quantity) {
    this.faces = faces;
    this.rollTimes = quantity;
  }

  public Integer roll(){
    Integer total = 0;
    Random rand = RandomProvider.getInstance();
    for(int i = 0; i < this.rollTimes; i++) {
      total += rand.nextInt(MIN_DICE_FACE, faces);
    }
    return total;
  }
}
