package com.quiz.QuizApp.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtservice;
        private final UserDetailsService userDetailsService;

        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                        @NonNull FilterChain filterChain)
                        throws ServletException, IOException {

                final String authHeader = request.getHeader("Authorization");
                System.out.println("in JWTAuthfilter, authhearder value " + authHeader);

                 final String jwt ;
                final String userEmail;
               if (authHeader == null || authHeader.startsWith("Bearer ")) {
                      //   if (authHeader == null) {
                        // this block is used for register and login where authgeader is nulll
                        System.out.println("inside JWTauthfilter  .. authHeader is null and returning");
                        filterChain.doFilter(request, response);
                        return;
                }
                /* 
                String jwt = authHeader;
                String[] loggedIndata = authHeader.split("isMyUserLoggedInEnd");
                System.out.println("inside JWTauthfilter  .. loggedIndata arr=" + Arrays.toString(loggedIndata));
                String isMyUserLoggedIn = null;
                if (loggedIndata.length > 1) {
                        jwt = loggedIndata[1];
                        String[] loggedInHeaderData = loggedIndata[0].split("isMyUserLoggedInStart");
                        System.out.println("inside JWTauthfilter  .. loggedInHeaderData arr="
                                        + Arrays.toString(loggedInHeaderData));
                        if (loggedInHeaderData.length > 1) {
                                isMyUserLoggedIn = loggedInHeaderData[1];
                        }

                }
                System.out.println("inside JWTauthfilter  .. isMyUserLoggedIn=" + isMyUserLoggedIn);
                System.out.println("inside JWTauthfilter  .. before authHeader.substring=" + authHeader);
                */
                jwt = authHeader.substring(6);
               // jwt = jwt.substring(7);

                System.out.println("inside JWTauthfilter printing token " + jwt);
                userEmail = jwtservice.extractUsername(jwt);
                System.out.println("inside JWTauthfilter printing email " + userEmail);

                //System.out.println("SecurityContextHolder getContext value "  + SecurityContextHolder.getContext().toString());

                //System.out.println("SecurityContextHolder getContext getAuthentication valu "  + SecurityContextHolder.getContext().getAuthentication());

                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                //if (userEmail != null) {

                        System.out.println("inside JWTauthfilter .. inside if");
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                        System.out.println("inside userdetails getauthorities .. inside if2 "
                                        + userDetails.getAuthorities());
                        System.out.println("inside JWTauthfilter .. after userDetails " + userDetails);
                        if (jwtservice.isTokenVaid(jwt, userDetails)) {
                                     //   && (isMyUserLoggedIn == null || isMyUserLoggedIn == "false")) {

                                System.out.println("inside JWTauthfilter .. inside if2 ");
                                System.out.println("inside userdetails getauthorities .. inside if2 "
                                                + userDetails.getAuthorities());

                                SecurityContext context = SecurityContextHolder.createEmptyContext();

                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                userDetails, null, userDetails.getAuthorities());
                                System.out.println(
                                                "inside JWTauthfilter .. after UsernamePasswordAuthenticationToken "
                                                                + authToken);
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                System.out.println("inside JWTauthfilter .. after authToken.setDetails");
                                context.setAuthentication(authToken);
                                SecurityContextHolder.setContext(context);
                                // SecurityContextHolder.getContext().setAuthentication(
                                // SecurityContextHolder.getContext().getAuthentication());

                                // SecurityContextHolder.getContext().setAuthentication(authToken);
                                System.out.println("inside JWTauthfilter .. after setAuthentication");
                        }
                        System.out.println(
                                        "inside if block Jwtauthfilter SecurityContextHolder getContext getAuthentication value   "
                                                        + SecurityContextHolder.getContext().getAuthentication());
                }

                System.out.println("inside JWTauthfilter .. after if");
                filterChain.doFilter(request, response);
                // throw new UnsupportedOperationException("Unimplemented method
                // 'doFilterInternal'");
        }

}
