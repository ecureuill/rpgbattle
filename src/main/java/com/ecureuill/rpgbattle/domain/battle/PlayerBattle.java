package com.ecureuill.rpgbattle.domain.battle;

import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_battle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerBattle {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "player_id")
  private Player player;
  @ManyToOne
  @JoinColumn(name = "battle_id")
  private Battle battle;

  @Override
  public String toString(){
    return "PlayerBattle{" +
            "id=" + id +
            ", player=" + player.getUsername() +
            ", battle=" + battle.getId() +
            '}';
  }
  public PlayerBattle updatePlayer(Player updatedPlayer) {
    this.player = updatedPlayer;
    return this;
  }
}
