package com.ecureuill.rpgbattle.application.dtos;

import jakarta.validation.constraints.NotBlank;

public record BattleCreateRequest(
  @NotBlank
  String playerOne,
  @NotBlank
  String playerTwo
) {

}
