package com.ecureuill.rpgbattle.application.dtos;

import com.ecureuill.rpgbattle.domain.user.User;

public record UserResponse(
  String username,
  String email
) {
  public UserResponse(User user) {
    this(user.getUsername(), user.getEmail());
  }
}
