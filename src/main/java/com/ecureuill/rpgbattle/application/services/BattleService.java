package com.ecureuill.rpgbattle.application.services;

import org.springframework.stereotype.Service;

import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Player;
import com.ecureuill.rpgbattle.infrastructure.repositories.BattleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleService {

  private final BattleRepository battleRepository;
  private final PlayerService playerService;

  public Battle createBattle(BattleCreateRequest battleUsers) throws InvalidBattleParametersException {

    if(battleUsers.playerOne().equals(battleUsers.playerTwo())){
      throw new InvalidBattleParametersException("Player One and Player Two should not be the same");
    }
    try {
      Player playerOne = playerService.findByUsername(battleUsers.playerOne());
      Player playerTwo = playerService.findByUsername(battleUsers.playerTwo());      
      Battle battle = new Battle(playerOne, playerTwo);
      return battleRepository.save(battle);
    } catch (PlayerNotFoundException e) {
      throw new InvalidBattleParametersException(e.getMessage(), e.initCause(e));
    }
  }

}
