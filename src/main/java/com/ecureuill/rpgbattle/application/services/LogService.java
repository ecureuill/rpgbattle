package com.ecureuill.rpgbattle.application.services;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.ecureuill.rpgbattle.application.exceptions.BattleNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.infrastructure.repositories.BattleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
  private final BattleRepository battleRepository;
  public Battle getLogs(UUID battleId) throws BattleNotFoundException {
    return battleRepository.findById(battleId).orElseThrow(() -> new BattleNotFoundException(battleId));
  }
  
}
