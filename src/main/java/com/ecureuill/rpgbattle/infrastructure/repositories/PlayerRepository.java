package com.ecureuill.rpgbattle.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.rpgbattle.domain.battle.Player;

public interface PlayerRepository extends JpaRepository<Player, String> {
  
}
