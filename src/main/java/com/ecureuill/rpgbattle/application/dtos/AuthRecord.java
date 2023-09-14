package com.ecureuill.rpgbattle.application.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthRecord(
  @NotBlank
  String username,
  @NotBlank
  String password
) {
  
}
