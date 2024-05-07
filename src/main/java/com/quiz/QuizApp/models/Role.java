package com.quiz.QuizApp.models;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static com.quiz.QuizApp.models.Permission.ADMIN_READ;
import static com.quiz.QuizApp.models.Permission.ADMIN_WRITE;
import static com.quiz.QuizApp.models.Permission.ADMIN_UPDATE;
import static com.quiz.QuizApp.models.Permission.ADMIN_DELETE;
import static com.quiz.QuizApp.models.Permission.USER_READ;
import static com.quiz.QuizApp.models.Permission.USER_WRITE;
import static com.quiz.QuizApp.models.Permission.USER_UPDATE;
import static com.quiz.QuizApp.models.Permission.USER_DELETE;

@RequiredArgsConstructor
public enum Role {

        USER(
                        Set.of(
                                        USER_READ,
                                        USER_DELETE,
                                        USER_UPDATE,
                                        USER_WRITE)),

        ADMIN(
                        Set.of(
                                        ADMIN_READ,
                                        ADMIN_UPDATE,
                                        ADMIN_DELETE,
                                        ADMIN_WRITE,
                                        USER_READ,
                                        USER_DELETE,
                                        USER_UPDATE,
                                        USER_WRITE))

        ;

        @Getter
        private final Set<Permission> permissions;

        public List<SimpleGrantedAuthority> getAuthorities() {
                System.out.println("in role .java  before  authority ");
                var authorities = getPermissions()
                                .stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                                .collect(Collectors.toList());

                System.out.println(" after  authority ");
                System.out.println(" after  authority name=" + this.name());
                authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

                return authorities;
        }
}
