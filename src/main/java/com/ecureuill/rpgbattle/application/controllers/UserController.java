package com.ecureuill.rpgbattle.application.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.ecureuill.rpgbattle.application.services.UserService;
import com.ecureuill.rpgbattle.domain.user.User;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @Transactional
  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest data, UriComponentsBuilder uriBuilder){
    User user = userService.save(data);
    var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
    return ResponseEntity.created(uri).body(new UserResponse(user));
  }

  @GetMapping
  public List<UserResponse> findAll(){
    return userService.getAll().stream().map(UserResponse::new).collect(Collectors.toList());
  }
}
