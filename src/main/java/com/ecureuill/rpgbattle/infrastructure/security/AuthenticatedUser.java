package com.ecureuill.rpgbattle.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {
  public static String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
