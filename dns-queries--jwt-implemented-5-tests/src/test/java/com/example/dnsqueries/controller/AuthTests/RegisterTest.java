package com.example.dnsqueries.controller.AuthTests;
import com.example.dnsqueries.dto.RegisterRequestDto;
import com.example.dnsqueries.entity.enums.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void register() throws Exception {

        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("secret")
                .role(Role.USER)
                .build();


        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token", Matchers.notNullValue()))
                .andDo(result -> { String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);
        String accessToken = jsonNode.get("access_token").asText();
        System.out.println("Received access token: " + accessToken);});
    }

//    @Test
//    public void login() throws Exception {
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
//
//        LoginRequestDto requestDto =  LoginRequestDto.builder()
//                .email("john@example.com")
//                .password("john")
//                .build();
//
//        String json = objectMapper.writeValueAsString(requestDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists());
//    }
}
