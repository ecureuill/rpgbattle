package com.ecureuill.rpgbattle.domain.character;

import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "characters")
@Data
public class Character {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private Type type;
  private String specie;
  private Integer life;
  private Integer strength;
  private Integer defence;
  private Integer agility;
  @Embedded
  private Dice dice;
  
  public void setDice(Dice dice) {
    this.dice = dice;
  }

  public void setDice(String diceValue) {
    var values = diceValue.split("d");
    var diceQuantity = Integer.valueOf(values[0]);
    var diceFace = Integer.valueOf(values[1]);
    this.dice = new Dice(diceFace, diceQuantity);
  }
}