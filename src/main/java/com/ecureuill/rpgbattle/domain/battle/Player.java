package com.ecureuill.rpgbattle.domain.battle;

import com.ecureuill.rpgbattle.domain.character.Character;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {
  @Id
  private String userName;
  @OneToOne
  private Character character;

}
