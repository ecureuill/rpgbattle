package com.ecureuill.rpgbattle.domain.battle;

import java.util.List;
import java.util.UUID;
import com.ecureuill.rpgbattle.domain.character.Character;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String username;
  @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
  private List<PlayerBattle> battles;
  @OneToOne
  private Character character;

  @Override
  public String toString() {
    return "Player{"+
    "username='" + username + '\'' +
    ", character=" + character.getId() +
    '}';
  }
}
