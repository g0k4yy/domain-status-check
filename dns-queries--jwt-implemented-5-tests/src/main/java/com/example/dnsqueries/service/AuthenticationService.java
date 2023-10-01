package com.example.dnsqueries.service;

import com.example.dnsqueries.dto.LoginRequestDto;
import com.example.dnsqueries.dto.AuthResponseDto;
import com.example.dnsqueries.dto.RegisterRequestDto;
import com.example.dnsqueries.entity.User;
import com.example.dnsqueries.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDto request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println("TEST");
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .accessToken(jwtToken)
                .build();
    }
}
