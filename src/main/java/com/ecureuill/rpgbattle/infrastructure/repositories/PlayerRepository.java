package com.ecureuill.rpgbattle.infrastructure.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecureuill.rpgbattle.domain.battle.Player;

public interface PlayerRepository extends JpaRepository<Player, String> {

  Optional<Player> findByUsername(String username);
  
}
