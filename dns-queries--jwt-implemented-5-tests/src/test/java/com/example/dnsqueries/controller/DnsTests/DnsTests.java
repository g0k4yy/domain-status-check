package com.example.dnsqueries.controller.DnsTests;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.example.dnsqueries.entity.enums.Role.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DnsTests {

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
    public void makeRequestForAll() throws Exception {

        LoginRequestDto requestDto = LoginRequestDto.builder()
                .email("test@mail.com")
                .password("password")
                .build();

        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists())
                .andReturn();

        String responseContent = loginResult.getResponse().getContentAsString();
        String accessToken = JsonPath.read(responseContent, "$.access_token");

        mockMvc.perform(MockMvcRequestBuilders.get("/dns-project/make-request-for-all")
                        .header("Authorization", "Bearer " + accessToken) // Include the access token in the Authorization header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }
}

//    @Test
//    public void getLastStatus() throws Exception {
//
//    }

