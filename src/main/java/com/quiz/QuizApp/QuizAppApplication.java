package com.quiz.QuizApp;

import static com.quiz.QuizApp.models.Role.ADMIN;
import static com.quiz.QuizApp.models.Role.USER;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.quiz.QuizApp.auth.AuthenticationService;
import com.quiz.QuizApp.auth.RegisterRequest;

@SpringBootApplication
public class QuizAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
		System.out.println("Running.....");
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000")
				.allowedHeaders("*").allowedMethods("GET","PUT","POST","DELETE","PATCH","OPTIONS");
			}
		};
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.first_name("Admin")
					.last_name("Admin")
					.email("admin@mail.com")
					.passwrd("password")
					.role(ADMIN)
					.build();

			var user = RegisterRequest.builder()
					.first_name("user")
					.last_name("Adusermin")
					.email("user@mail.com")
					.passwrd("password")
					.role(USER)
					.build();

		};
	}

}
