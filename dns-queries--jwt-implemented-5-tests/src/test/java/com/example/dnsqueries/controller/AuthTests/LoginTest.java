package com.example.dnsqueries.controller.AuthTests;
import com.example.dnsqueries.dto.LoginRequestDto;
import com.example.dnsqueries.dto.RegisterRequestDto;
import com.example.dnsqueries.entity.User;
import com.example.dnsqueries.entity.enums.Role;
import com.example.dnsqueries.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.example.dnsqueries.entity.enums.Role.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void login() throws Exception {
        //        User mockUser = User.builder()
//                .id(987243L)
//                .username("John")
//                .password(passwordEncoder.encode("john"))
//                .email("john@example.com")
//                .role(USER)
//                .build();
//
//        Optional<User> optionalOfMockUser = Optional.of(mockUser);
//        when(userRepository.findByEmail("john@example.com")).thenReturn(optionalOfMockUser);
//
//        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        LoginRequestDto requestDto =  LoginRequestDto.builder()
                .email("test@mail.com")
                .password("password")
                .build();

        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists())
                .andDo(result -> {
            String responseContent = result.getResponse().getContentAsString();
            String accessToken = JsonPath.read(responseContent, "$.access_token");
            System.out.println("Received Access Token: " + accessToken);
        });
    }
}


