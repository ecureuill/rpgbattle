package com.ecureuill.rpgbattle.domain.battle;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Initiativa {
  private Integer playerOneDiceValue;
  private Integer playerTwoDiceValue;
  @Transient
  private Player player;
}
