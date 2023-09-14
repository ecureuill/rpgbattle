package com.ecureuill.rpgbattle.application.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.application.dtos.CharacterResponse;
import com.ecureuill.rpgbattle.application.exceptions.CharacterAlreadyExistException;
import com.ecureuill.rpgbattle.application.exceptions.CharacterNotFoundException;
import com.ecureuill.rpgbattle.application.services.CharacterService;
import com.ecureuill.rpgbattle.domain.character.Character;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
  private final CharacterService characterService;

  @Operation(
    summary = "Create a new character", 
    description = "Create a new HERO or MONSTER character",
    tags = {"characters"},
    responses = {
      @ApiResponse(responseCode = "201", description = "Character created",links={@Link(description="Get character")}),
      @ApiResponse(responseCode = "400", description = "Bad request"),
  })
  @Transactional
  @PostMapping
  public ResponseEntity<CharacterResponse> createCharacter(@RequestBody @Valid CharacterRequest characterRequest, UriComponentsBuilder uriBuilder) {
    Character character = characterService.createCharacter(characterRequest);
    var uri = uriBuilder.path("/characters/{id}").buildAndExpand(character.getId()).toUri();
    return ResponseEntity.created(uri).body(new CharacterResponse(character));
  }

  @Operation(
    summary = "All characters data", 
    description = "Retrieves a list of all characters",
    tags = {"characters"},
    responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
  })
  @GetMapping
  public ResponseEntity<List<CharacterResponse>> getAllCharacters() {
    List<Character> characters = characterService.getAllCharacters();
    return ResponseEntity.ok().body(characters.stream().map(CharacterResponse::new).collect(Collectors.toList())); 
  }

  @Operation(
    summary = "One character", 
    description = "Retrieves a specific character by it specie",
    tags = {"characters"},
    responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
  })
  @GetMapping("/{specie}")
  public ResponseEntity<CharacterResponse> getCharacterBySpecie(@PathVariable String specie) throws CharacterNotFoundException {
    Character character = characterService.getCharacterBySpecie(specie);
    return ResponseEntity.ok().body(new CharacterResponse(character));
  }

  @Operation(
    summary = "Update one character", 
    description = "Updates a specific character by it specie",
    tags = {"characters"},
    responses = {
      @ApiResponse(responseCode = "200", description = "Updated"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
  })
  @Transactional
  @PutMapping("/{specie}")
  public ResponseEntity<CharacterResponse> updateCharacter(@PathVariable String specie, @RequestBody @Valid CharacterRequest characterRequest) throws CharacterNotFoundException, CharacterAlreadyExistException {
    Character character = characterService.updateCharacter(specie, characterRequest);
    return ResponseEntity.ok().body(new CharacterResponse(character));
  }

  @Operation(
    summary = "Delete one character", 
    description = "Deletes a specific character by it specie",
    tags = {"characters"},
    responses = {
      @ApiResponse(responseCode = "204", description = "No content"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
  })
  @Transactional
  @DeleteMapping("/{specie}")
  public ResponseEntity<Void> deleteCharacter(@PathVariable String specie) {
    characterService.deleteCharacter(specie);
    return ResponseEntity.noContent().build();
  }
}
