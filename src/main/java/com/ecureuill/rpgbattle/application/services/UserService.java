package com.ecureuill.rpgbattle.application.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecureuill.rpgbattle.application.controllers.UserRequest;
import com.ecureuill.rpgbattle.domain.user.User;
import com.ecureuill.rpgbattle.infrastructure.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public User save(@Valid UserRequest data) {
    User user = data.toEntity();
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
