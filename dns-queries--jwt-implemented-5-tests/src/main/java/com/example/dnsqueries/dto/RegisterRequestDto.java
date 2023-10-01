package com.example.dnsqueries.dto;


import com.example.dnsqueries.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    private String username;
    private String email;
    private String password;
    private Role role;
}
