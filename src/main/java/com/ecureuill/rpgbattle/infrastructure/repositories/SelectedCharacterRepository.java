package com.ecureuill.rpgbattle.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;

public interface SelectedCharacterRepository extends JpaRepository<SelectedCharacter, UUID> {
  Optional<SelectedCharacter> findBySpecie(String specie);  
}
