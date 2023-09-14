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
import org.springframework.web.util.UriComponentsBuilder;

import com.ecureuill.rpgbattle.application.dtos.BattleCreateRequest;
import com.ecureuill.rpgbattle.application.dtos.BattleResponse;
import com.ecureuill.rpgbattle.application.dtos.BattleSelectCharacterRequest;
import com.ecureuill.rpgbattle.application.dtos.InitiativeResponse;
import com.ecureuill.rpgbattle.application.dtos.TurnResponse;
import com.ecureuill.rpgbattle.application.exceptions.BattleNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.BattleStateException;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.services.BattleService;
import com.ecureuill.rpgbattle.domain.battle.Battle;
import com.ecureuill.rpgbattle.domain.battle.Turn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/battles")
@RequiredArgsConstructor
public class BattleController {
  private final BattleService battleService;

  @Operation(
    summary = "Create a new battle", 
    description = "Create a new battle between two users",
    tags = {"battles"},
    responses = {
      @ApiResponse(responseCode = "201", description = "Battle created",links={@Link(description="Get battle")}),
      @ApiResponse(responseCode = "400", description = "Invalid battle parameters"),
  })
  @Transactional
  @PostMapping
  public ResponseEntity<BattleResponse> createBattle(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usernames of who will battle") @RequestBody BattleCreateRequest battleUsers, UriComponentsBuilder uriBuilder) throws InvalidBattleParametersException, BattleStateException {
    Battle battle = battleService.createBattle(battleUsers);
    var uri = uriBuilder.path("/battles/{id}").buildAndExpand(battle.getId()).toUri();
    return ResponseEntity.created(uri).body(new BattleResponse(battle));
  }

  @Operation(
    summary = "Select character", 
    description = "Select the character you will battle", 
    tags = {"battles"},
    responses = {
      @ApiResponse(responseCode = "200", description = "Character selected"),
      @ApiResponse(responseCode = "400", description = "Invalid battle parameters"),
  })
  @Transactional
  @PostMapping("/{battleId}/{player}")
  public ResponseEntity<BattleResponse> selectCharacter(@Parameter(description = "The battle id") @PathVariable UUID battleId, @Parameter(description = "The player username taht will slect a character") @PathVariable String player, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The character specie name") @RequestBody @Valid BattleSelectCharacterRequest data) throws InvalidBattleParametersException {
    try {
      Battle battle = battleService.selectCharacter(battleId, player, data);
      return ResponseEntity.ok(new BattleResponse(battle));  
    } catch (Exception e) {
      throw new InvalidBattleParametersException("Character not selected\n"+e.getMessage(), e);
    }
  }

  @Operation(
    summary = "Initiative", 
    description = "Determine the battle's initiative. In case of tie, you need to send a new request.", 
    tags = {"battles"},
    responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Invalid battle parameters"),
  })
  @Transactional
  @PostMapping("/{battleId}/Initiative")
  public ResponseEntity<InitiativeResponse> determineInitiative(@Parameter(description = "The battle id") @PathVariable UUID battleId) throws BattleNotFoundException, InvalidBattleParametersException {
    try {
      Battle battle = battleService.determineInitiative(battleId);
      return ResponseEntity.ok(new InitiativeResponse(battle.getInitiative()));
    } catch (Exception e) {
      throw new InvalidBattleParametersException("Battle not started\n"+e.getMessage(), e);
    }
  }  

  @Operation(
    summary = "Take turns", 
    description = "Will roll the dices for attack, defense and damage. The first request, will be for the initiative's player attack, then it will alternate between players, until a character loose all life points.", 
    tags = {"battles"},
    responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Invalid battle parameters"),
  })
  @Transactional
  @PostMapping("/{battleId}/turns")
  public ResponseEntity<TurnResponse> turns(@Parameter(description = "The battle id") @PathVariable UUID battleId) throws BattleNotFoundException, InvalidBattleParametersException {
    try {
      Battle battle = battleService.takeTurns(battleId);
      return ResponseEntity.ok(new TurnResponse(battle.getCurrentTurn().orElse(new Turn())));
    } catch (Exception e) {
      throw new InvalidBattleParametersException("Battle not started\n"+e.getMessage(), e);
    }
  }

  @Operation(
    summary = "All battles data", 
    description = "Retrieve a list of all battles", 
    tags = {"battles"},
    responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Invalid battle parameters"),
  })  
  @GetMapping
  public ResponseEntity<List<BattleResponse>> getAllBattles(@RequestParam(required=false) MultiValueMap<String, String> queryParams) {
    return ResponseEntity.ok().body(battleService.getAllBattles(queryParams).stream().map(BattleResponse::new).collect(Collectors.toList()));
  }


  @Operation(
    summary = "Battle data", 
    description = "Retrieve a specific battle", 
    tags = {"battles"},
    responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Invalid battle parameters"),
  })  
  @GetMapping("/{uuid}")
  public ResponseEntity<BattleResponse> getBattleByUUID(@PathVariable UUID uuid) throws BattleNotFoundException {
    return ResponseEntity.ok().body(new BattleResponse(battleService.getBattleById(uuid)));
  }

  
}
