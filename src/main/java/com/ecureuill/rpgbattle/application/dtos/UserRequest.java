package com.ecureuill.rpgbattle.application.dtos;

import com.ecureuill.rpgbattle.domain.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
  @NotBlank
  String username,
  @NotBlank
  @Size(min=8)
  String password,
  @Email
  String email
) {
  public User toEntity(){
    return new User(null, username, email, password);
  }
}
