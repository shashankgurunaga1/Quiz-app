package com.quiz.QuizApp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quiz.QuizApp.config.JwtService;
import com.quiz.QuizApp.models.Role;
import com.quiz.QuizApp.models.User;
import com.quiz.QuizApp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                System.out.println("in authneticationservice request object " + request);
                // System.out.println("in authneticationservice role user object " + Role.USER);
                System.out.println("in authneticationservice request  getrole object " + request.getRole());

                var user = User.builder()
                                .first_name(request.getFirst_name())
                                .last_name(request.getLast_name())
                                .email(request.getEmail())
                                .role(request.getRole())

                                .passwrd(passwordEncoder
                                                .encode(request.getPasswrd()))
                                .build();
                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                System.out.println(" token genrated    " + jwtToken);
                return AuthenticationResponse
                                .builder().token(jwtToken)
                                .build();
                // throw new UnsupportedOperationException("Unimplemented method 'register'");
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                System.out.println("in authenticationservice java: authenticate");
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPasswrd()));

                System.out.println("in authenticationservice java: authenticat2e");
                var user = repository.findByEmail(request.getEmail()).orElseThrow();
                System.out.println("in authenticationservice java , post login, logged in user       :" + user);
                var jwtToken = jwtService.generateToken(user);
                System.out.println("in Authenticationservice java , post login, token for logged in user " + jwtToken);
                return AuthenticationResponse.builder().token(jwtToken).build();

                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method
                // 'authenticate'");
        }

}
