package com.quiz.QuizApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static com.quiz.QuizApp.models.Permission.ADMIN_READ;
import static com.quiz.QuizApp.models.Permission.ADMIN_WRITE;
import static com.quiz.QuizApp.models.Permission.ADMIN_UPDATE;
import static com.quiz.QuizApp.models.Permission.ADMIN_DELETE;
import static com.quiz.QuizApp.models.Permission.USER_READ;
import static com.quiz.QuizApp.models.Permission.USER_WRITE;
import static com.quiz.QuizApp.models.Permission.USER_UPDATE;
import static com.quiz.QuizApp.models.Permission.USER_DELETE;
import lombok.RequiredArgsConstructor;
import static com.quiz.QuizApp.models.Role.ADMIN;
import static com.quiz.QuizApp.models.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JWTAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  /// which configuration ensure all the other services that creates a binding
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/auth/**").permitAll()
            //.requestMatchers("/quiz/updateuserinfo/**").permitAll()
            .requestMatchers("/api/v1/user-controller").hasAnyRole(ADMIN.name(), USER.name())
            .requestMatchers(GET, "/api/v1/user-controller")
            .hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
            .requestMatchers(POST, "/api/v1/user-controller")
            .hasAnyAuthority(ADMIN_WRITE.name(), USER_WRITE.name())
            .requestMatchers(PUT, "/api/v1/user-controller")
            .hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
            .requestMatchers(DELETE, "/api/v1/user-controller")
            .hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())
            .requestMatchers("/quiz/**").hasAnyRole(ADMIN.name())
            // .requestMatchers("/quiz/updateuserinfo/**").hasAnyRole(ADMIN.name())
            .requestMatchers("/quiz/deleteuserinfo/**").hasAnyRole(ADMIN.name())
            // .requestMatchers("/quiz/updateuserinfo").hasAnyRole(ADMIN.name())
            .requestMatchers(GET, "/quiz/getuserinfo").hasAnyAuthority(ADMIN_READ.name())
           // .requestMatchers(POST, "/quiz/adduser").hasAnyAuthority(ADMIN_WRITE.name())
            // .requestMatchers(PUT,
            // "/quiz/updateuserinfo").hasAnyAuthority(ADMIN_UPDATE.name())
            // .requestMatchers(PUT,
            // "/quiz/updateuserinfo/**").hasAnyAuthority(ADMIN_UPDATE.name())
           // .requestMatchers(DELETE, "/quiz/deleteuserinfo/**").hasAnyAuthority(ADMIN_DELETE.name())

            .anyRequest().authenticated())
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();

    // return http.build();
  }
}
