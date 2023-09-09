package com.ecureuill.rpgbattle.application.services;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.infrastructure.repositories.CharacterRepository;
import com.ecureuill.rpgbattle.utils.DataFaker;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {
  @Mock
  private CharacterRepository characterRepository;
  @InjectMocks
  private CharacterService characterService;
  
  private final DataFaker dataFaker = new DataFaker();

  @DisplayName("Should save a character")
  @Test
  public void shouldSaveACharacter() {
    CharacterRequest characterRequest = dataFaker.generaCharacterRequest();
    
    Mockito.when(characterRepository.save(Mockito.any(Character.class))).thenReturn(characterRequest.toEntity());
    characterService.createCharacter(characterRequest);
    
    Mockito.verify(characterRepository, Mockito.times(1)).save(Mockito.any(Character.class));
  }

  @DisplayName("Should get all characters")
  @Test
  void testGetAllCharacters() {
    List<Character> expectedCharacters = dataFaker.generateCharacters();

    Mockito.when(characterRepository.findAll()).thenReturn(expectedCharacters);
    List<Character> characters = characterService.getAllCharacters();
    
    Mockito.verify(characterRepository, Mockito.times(1)).findAll();
    Assertions.assertEquals(expectedCharacters.size(), characters.size());
  }

}
