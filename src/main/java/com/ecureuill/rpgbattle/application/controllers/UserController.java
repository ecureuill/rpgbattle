package com.ecureuill.rpgbattle.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.ecureuill.rpgbattle.application.services.UserService;
import com.ecureuill.rpgbattle.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest data, UriComponentsBuilder uriBuilder){
    User user = userService.save(data);
    var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
    return ResponseEntity.created(uri).body(new UserResponse(user));
  }
}
