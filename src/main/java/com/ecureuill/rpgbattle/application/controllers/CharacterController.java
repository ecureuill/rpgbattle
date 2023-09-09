package com.ecureuill.rpgbattle.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.application.dtos.CharacterResponse;
import com.ecureuill.rpgbattle.application.services.CharacterService;
import com.ecureuill.rpgbattle.domain.character.Character;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
  
  private final CharacterService characterService;

  @PostMapping
  public ResponseEntity<CharacterResponse> createCharacter(@RequestBody @Valid CharacterRequest characterRequest, UriComponentsBuilder uriBuilder) {
    Character character = characterService.createCharacter(characterRequest);
    var uri = uriBuilder.path("/characters/{id}").buildAndExpand(character.getId()).toUri();
    return ResponseEntity.created(uri).body(new CharacterResponse(character));
  }
}
