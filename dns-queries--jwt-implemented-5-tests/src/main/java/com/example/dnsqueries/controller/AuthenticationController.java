package com.example.dnsqueries.controller;

import com.example.dnsqueries.dto.LoginRequestDto;
import com.example.dnsqueries.dto.AuthResponseDto;
import com.example.dnsqueries.dto.RegisterRequestDto;
import com.example.dnsqueries.service.AuthenticationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }



}
