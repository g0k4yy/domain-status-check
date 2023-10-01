package com.example.dnsqueries;

import com.example.dnsqueries.dto.RegisterRequestDto;
import com.example.dnsqueries.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import static com.example.dnsqueries.entity.enums.Role.ADMIN;
import static com.example.dnsqueries.entity.enums.Role.USER;

@SpringBootApplication()
@EntityScan("com.example.dnsqueries.entity")
public class DnsQueriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnsQueriesApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(


			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequestDto.builder()
					.username("Admin2")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var user = RegisterRequestDto.builder()
					.username("TestUser")
					.email("test@mail.com")
					.password("password")
					.role(USER)
					.build();
			System.out.println("USER token: " + service.register(user).getAccessToken());

		};
	}

}
