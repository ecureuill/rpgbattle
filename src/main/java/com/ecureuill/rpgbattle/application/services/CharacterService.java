package com.ecureuill.rpgbattle.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecureuill.rpgbattle.application.dtos.CharacterRequest;
import com.ecureuill.rpgbattle.application.exceptions.CharacterAlreadyExistException;
import com.ecureuill.rpgbattle.application.exceptions.CharacterNotFoundException;
import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.infrastructure.repositories.CharacterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacterService {
  private final CharacterRepository characterRepository;

  public Character createCharacter(CharacterRequest characterRequest) {
    Character character = characterRequest.toEntity();
    return characterRepository.save(character);
  }

  public List<Character> getAllCharacters() {
    return characterRepository.findAll();
  }

  public Character getCharacterBySpecie(String specie) throws CharacterNotFoundException {
    return characterRepository.findBySpecie(specie).orElseThrow(() -> new CharacterNotFoundException("Character of specie " + specie + " not found"));
  }

  public Character updateCharacter(String specie, CharacterRequest characterRequest) throws CharacterNotFoundException, CharacterAlreadyExistException {

    Character character = characterRepository.findBySpecie(specie).orElseThrow(() -> new CharacterNotFoundException(specie));

    if(!character.getSpecie().equals(specie) && characterRepository.findBySpecie(character.getSpecie()).isPresent()){
      throw new CharacterAlreadyExistException(specie);
    }

    Character characterToSave = characterRequest.toEntity();
    characterToSave.setId(character.getId());
    return characterRepository.save(characterToSave);
  }
}
