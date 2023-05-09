package com.example.GARA_API;

import com.example.GARA_API.model.Role;
import com.example.GARA_API.model.User;
import com.example.GARA_API.services.appUser.AppUserServices;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
public class DotsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotsApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Argon2PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder();
	}

	@Bean
	CommandLineRunner run(AppUserServices appUserServices) {
		return args -> {
			appUserServices.saveRole(new Role( "ROLE_USER"));
			appUserServices.saveRole(new Role( "ROLE_ADMIN"));
			appUserServices.saveRole(new Role( "ROLE_MANAGER"));
			appUserServices.saveRole(new Role( "ROLE_EMPLOYEE"));

			appUserServices.saveAppUser(new User( "Tuan", "Nguyen", "tuanxsokoh", "tuanxsokoh@gmail.com",
					"123456", new HashSet<>()));

			appUserServices.addRoleToAppUser("tuanxsokoh", "ROLE_ADMIN");
		};
	}


}
