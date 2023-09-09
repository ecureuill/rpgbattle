package com.ecureuill.rpgbattle.domain.character;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dice {
  private Integer faces;
  private Integer quantity;
}
