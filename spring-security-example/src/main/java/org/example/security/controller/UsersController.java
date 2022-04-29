package org.example.security.controller;

import org.example.security.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UsersController {
    private final UserDetailsService userDetailsService;

    public UsersController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    String home() {
        return "home";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest request) {

    }
}
