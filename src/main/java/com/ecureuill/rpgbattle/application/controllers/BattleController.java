package com.ecureuill.rpgbattle.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.dtos.BattleCreateResponse;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.services.BattleService;
import com.ecureuill.rpgbattle.domain.battle.Battle;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/battles")
@RequiredArgsConstructor
public class BattleController {

  private final BattleService battleService;

  @PostMapping
  public ResponseEntity<BattleCreateResponse> createBattle(@RequestBody BattleCreateRequest battleUsers) throws InvalidBattleParametersException {
    Battle battle = battleService.createBattle(battleUsers);
    return ResponseEntity.ok(new BattleCreateResponse(battle));
  }
  
}
