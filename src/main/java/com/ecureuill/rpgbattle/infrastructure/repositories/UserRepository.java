package com.ecureuill.rpgbattle.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.rpgbattle.domain.user.User;

public interface UserRepository extends JpaRepository<User, UUID>{

  Optional<User> findByUsername(Object subject);
}
