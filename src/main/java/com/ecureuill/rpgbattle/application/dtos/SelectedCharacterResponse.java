package com.ecureuill.rpgbattle.application.dtos;

import java.util.UUID;

import com.ecureuill.rpgbattle.domain.battle.SelectedCharacter;
import com.ecureuill.rpgbattle.domain.character.Type;
import com.ecureuill.rpgbattle.domain.dice.Dice;

public record SelectedCharacterResponse(
  UUID id,
  Type type,
  String specie,
  Integer life,
  Integer strength,
  Integer defence,
  Integer agility,
  Dice dice
) {

  public SelectedCharacterResponse(SelectedCharacter character) {
    this(character.getId(), character.getType(), character.getSpecie(), character.getLife(), character.getStrength(), character.getDefence(), character.getAgility(), character.getDice());
  }

}
