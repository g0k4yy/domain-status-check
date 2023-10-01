package com.example.dnsqueries.config;

import com.example.dnsqueries.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.dnsqueries.entity.enums.Role.ADMIN;
import static com.example.dnsqueries.entity.enums.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth ->
                        auth.requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/webjars/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-resources/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/bus/v3/api-docs/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/configuration/security")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/dns-project/add-domain")).hasAnyAuthority(ADMIN.name())
                                .anyRequest()
                                .authenticated())
                .sessionManagement( sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
