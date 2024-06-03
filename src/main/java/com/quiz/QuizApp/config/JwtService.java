package com.quiz.QuizApp.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.quiz.QuizApp.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "4D92199549E0F2EF009B4160F3582E5528A11A45017F3EF8";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println(" inside jwtservice extraclaim printing token value " + token);
        final Claims claims = extractAllClaims(token);
        if (claims == null) {
            System.out.println(" claims are null inside jwtservice");
        }
        System.out.println(" inside jwtservice extraclaim after extractclaim printing claims firstname "
                + claims.get("last_name"));
        System.out
                .println(" inside jwtservice extraclaim after extractclaim printing claims  role  "
                        + claims.get("role"));

        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails) {

        // Map<String, String> mp = new HashMap<>();
        extraClaims.put("first_name", userDetails.getFirst_name());
        extraClaims.put("role", userDetails.getRole().name());
        extraClaims.put("last_name", userDetails.getLast_name());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()).addClaims(extraClaims)
                // .setPayload("role:"+userDetails.)
                // .setPayload(userDetails.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenVaid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
        // throw new UnsupportedOperationException("Unimplemented method
        // 'isTokenExpired'");
    }

    private Date extractExpiration(String token) {
        // TODO Auto-generated method stub
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        // to generate a token we need to always create a signing key
    }

    private Key getSignInKey() {
        // TODO Auto-generated method stub
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getSignInKey'");
    }
}