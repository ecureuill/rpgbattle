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

import com.ecureuill.rpgbattle.application.dtos.UserRequest;
import com.ecureuill.rpgbattle.application.dtos.UserResponse;
import com.ecureuill.rpgbattle.application.services.UserService;
import com.ecureuill.rpgbattle.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @Operation(
    summary = "Create a user", 
    description = "Create a user to future battles",
    tags = {"users"},
    responses = {
      @ApiResponse(responseCode = "201", description = "Created", 
      links = {
        @Link(description = "Get user")
      }),
      @ApiResponse(responseCode = "400", description = "Bad request"),
  })
  @Transactional
  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest data, UriComponentsBuilder uriBuilder){
    User user = userService.save(data);
    var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
    return ResponseEntity.created(uri).body(new UserResponse(user));
  }

  @Operation(
    summary = "All users", 
    description = "Retrieves all users", 
    tags = {"users" }, 
    responses = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad request"),
      })
  @GetMapping
  public List<UserResponse> findAll(){
    return userService.getAll().stream().map(UserResponse::new).collect(Collectors.toList());
  }
}
