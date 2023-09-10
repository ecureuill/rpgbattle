package com.ecureuill.rpgbattle.application.services;

import org.springframework.stereotype.Service;

import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.infrastructure.repositories.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {

  private final PlayerRepository playerRepository;
  
  public Player findByUsername(String anyString) throws PlayerNotFoundException {
    return playerRepository.findById(anyString).orElseThrow(() -> new PlayerNotFoundException(anyString));
  }
  
}
