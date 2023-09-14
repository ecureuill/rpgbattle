package com.ecureuill.rpgbattle.application.dtos;

import com.ecureuill.rpgbattle.domain.character.Character;
import com.ecureuill.rpgbattle.domain.character.Type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CharacterRequest(
  @NotNull
  Type type,
  @NotBlank
  String specie,
  @Positive
  Integer life,
  @Positive
  Integer strength,
  @Positive
  Integer defence,
  @Positive
  Integer agility,
  @NotNull
  @Pattern(regexp = "[0-9]{1,2}d[0-9]{1,2}")
  String dice
) {

  public Character toEntity() {
    Character character = new Character();
    character.setType(type);
    character.setSpecie(specie);
    character.setLife(life);
    character.setStrength(strength);
    character.setDefence(defence);
    character.setAgility(agility);
    character.setDice(dice);
    return character;
  }

}
