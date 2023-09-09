package com.ecureuill.rpgbattle.application.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.application.exceptions.CharacterAlreadyExistException;
import com.ecureuill.rpgbattle.application.exceptions.CharacterNotFoundException;
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
  public void testSaveACharacter() {
    CharacterRequest characterRequest = dataFaker.generateCharacterRequest();
    
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

  @DisplayName("Should get one character by specie")
  @Test
  void testGetOneCharacterBySpecie() throws CharacterNotFoundException {
    Character expectedCharacter = dataFaker.generateCharacter();

    Mockito.when(characterRepository.findBySpecie(expectedCharacter.getSpecie())).thenReturn(Optional.of(expectedCharacter));
    Character character = characterService.getCharacterBySpecie(expectedCharacter.getSpecie());
    
    Mockito.verify(characterRepository, Mockito.times(1)).findBySpecie(expectedCharacter.getSpecie());
    Assertions.assertEquals(expectedCharacter.getSpecie(), character.getSpecie());
  }

  @DisplayName("Should throw CharacterNotFoundException when attempts to get by specie a not existent character")
  @Test
  void testGetOneCharacterBySpecie_CharacterDoesNotExist() {
    Mockito.when(characterRepository.findBySpecie(Mockito.anyString())).thenReturn(Optional.empty());
    
    Assertions.assertThrows(CharacterNotFoundException.class, () -> characterService.getCharacterBySpecie(""));
    
    Mockito.verify(characterRepository, Mockito.times(1)).findBySpecie(Mockito.anyString());
  }

  @DisplayName("Should update a character")
  @Test
  void testUpdateACharacter() throws CharacterNotFoundException, CharacterAlreadyExistException {
    CharacterRequest characterRequest = dataFaker.generateCharacterRequest();
    Character expectedCharacter = characterRequest.toEntity();

    Mockito.when(characterRepository.findBySpecie(Mockito.anyString())).thenReturn(Optional.of(expectedCharacter));
    Mockito.when(characterRepository.save(Mockito.any(Character.class))).thenReturn(expectedCharacter);
    characterService.updateCharacter(characterRequest.specie(), characterRequest);
    
    Mockito.verify(characterRepository, Mockito.times(1)).save(Mockito.any(Character.class));
    Mockito.verify(characterRepository, Mockito.times(1)).findBySpecie(Mockito.anyString());
  }

  @DisplayName("Should throw CharacterAlreadyExistException when attempt to update a character and replaced specie is already registered")
  @Test
  void testUpdateACharacter_CharacterAlreadyExists() {
    CharacterRequest characterRequest = dataFaker.generateCharacterRequest();
    Character expectedCharacter = characterRequest.toEntity();

    Mockito.when(characterRepository.findBySpecie("pathVariableSpecie")).thenReturn(Optional.of(expectedCharacter));
    Mockito.when(characterRepository.findBySpecie(characterRequest.specie())).thenReturn(Optional.of(dataFaker.generateCharacter()));

    Assertions.assertThrows(CharacterAlreadyExistException.class,() -> characterService.updateCharacter("pathVariableSpecie", characterRequest));
    
    Mockito.verify(characterRepository, Mockito.times(0)).save(Mockito.any(Character.class));
    Mockito.verify(characterRepository, Mockito.times(2)).findBySpecie(Mockito.anyString());
  }

  @DisplayName("Should throw CharacterNotFoundException when attempts to update a not existent character")
  @Test
  void testUpdateACharacter_CharacterDoesNotExist() {
    Mockito.when(characterRepository.findBySpecie(Mockito.anyString())).thenReturn(Optional.empty());
    
    Assertions.assertThrows(CharacterNotFoundException.class, () -> characterService.updateCharacter("", dataFaker.generateCharacterRequest()));
    
    Mockito.verify(characterRepository, Mockito.times(1)).findBySpecie(Mockito.anyString());
    Mockito.verify(characterRepository, Mockito.times(0)).save(Mockito.any(Character.class));
  }
}
