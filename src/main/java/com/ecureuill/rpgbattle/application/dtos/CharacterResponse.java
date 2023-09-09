package com.ecureuill.rpgbattle.application.dtos;

import java.util.UUID;

import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.character.Type;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public record CharacterResponse(
  UUID id,
  Type type,
  String specie,
  Integer life,
  Integer strength,
  Integer defence,
  Integer agility,
  Dice dice
) {

  public CharacterResponse(Character character) {
    this(character.getId(), character.getType(), character.getSpecie(), character.getLife(), character.getStrength(), character.getDefence(), character.getAgility(), character.getDice());
  }

}
