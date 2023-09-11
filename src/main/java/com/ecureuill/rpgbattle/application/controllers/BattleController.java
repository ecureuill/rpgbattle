package com.ecureuill.rpgbattle.application.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.dtos.BattleResponse;
import com.ecureuill.rpgbattle.application.exceptions.BattleNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.services.BattleService;
import com.ecureuill.rpgbattle.domain.battle.Battle;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/battles")
@RequiredArgsConstructor
public class BattleController {

  private final BattleService battleService;

  @PostMapping
  public ResponseEntity<BattleResponse> createBattle(@RequestBody BattleCreateRequest battleUsers) throws InvalidBattleParametersException {
    Battle battle = battleService.createBattle(battleUsers);
    return ResponseEntity.ok(new BattleResponse(battle));
  }

  @GetMapping
  public ResponseEntity<List<BattleResponse>> getAllBattles(@RequestParam(required=false) MultiValueMap<String, String> queryParams) {
    return ResponseEntity.ok().body(battleService.getAllBattles(queryParams).stream().map(BattleResponse::new).collect(Collectors.toList()));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<BattleResponse> getBattleByUUID(@PathVariable UUID uuid) throws BattleNotFoundException {
    return ResponseEntity.ok().body(new BattleResponse(battleService.getBattleById(uuid)));
  }

  
}
