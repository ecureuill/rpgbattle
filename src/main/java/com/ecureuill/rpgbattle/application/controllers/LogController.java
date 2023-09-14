package com.ecureuill.rpgbattle.application.controllers;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ecureuill.rpgbattle.application.dtos.LogResponse;
import com.ecureuill.rpgbattle.application.exceptions.BattleNotFoundException;
import com.ecureuill.rpgbattle.application.services.LogService;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LogController {
  private final LogService logService;

  @Operation(
    summary = "Battle logs", 
    description = "Retrieve Logs for a specific battle",
    tags = {"Logs"},
    responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request"),
  })
  @GetMapping("/logs/{battleId}")
  public ResponseEntity<LogResponse> getLogs(@PathVariable UUID battleId) throws BattleNotFoundException {
    Battle battle = logService.getLogs(battleId);
    return ResponseEntity.ok().body(LogResponse.of(battle));
  }
}
