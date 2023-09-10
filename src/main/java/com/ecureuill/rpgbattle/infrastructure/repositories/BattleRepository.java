package com.ecureuill.rpgbattle.infrastructure.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecureuill.rpgbattle.domain.battle.Battle;

public interface BattleRepository extends JpaRepository<Battle, UUID> {
}
