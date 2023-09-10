package com.ecureuill.rpgbattle.domain.character;

import java.util.UUID;

import com.ecureuill.rpgbattle.domain.dice.Dice;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "characters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Character {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private Type type;
  @Column(unique = true)
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

  public int producesDemage(int demageDiceValue) {
    return this.strength + demageDiceValue;
  }

  public void receiveDemage(int demage) {
    this.life -= demage;
  }
}
