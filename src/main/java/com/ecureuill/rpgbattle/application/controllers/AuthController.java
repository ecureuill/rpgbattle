package com.ecureuill.rpgbattle.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecureuill.rpgbattle.application.dtos.AuthRecord;
import com.ecureuill.rpgbattle.application.dtos.TokenRecord;
import com.ecureuill.rpgbattle.domain.user.User;
import com.ecureuill.rpgbattle.infrastructure.security.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager manager;
    private TokenService tokenService;

    public AuthController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenRecord> authenticate(@RequestBody @Valid AuthRecord data) {
        var token = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var authenticate = manager.authenticate(token);
        var tokenJwt = tokenService.generateToken((User) authenticate.getPrincipal());

        return ResponseEntity.ok(new TokenRecord(tokenJwt));
    }
}
