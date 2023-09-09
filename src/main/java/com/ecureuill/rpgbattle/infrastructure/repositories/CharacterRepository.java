package com.ecureuill.rpgbattle.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.rpgbattle.domain.character.Character;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

}
