package io.github.icon02.springbootauthenticationtemplate.util;

import io.github.icon02.springbootauthenticationtemplate.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final Long EXPIRATION_DURATION = 1000L * 60 * 60; // 1s * mins

    // TODO put this with your very own secret key into .env
    private static final String secret_key = "REPLACE_AND_PUT_INTO_ENV";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("email", String.class);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        // TODO put own claims
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername()) // user has no username, this returns email instead
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DURATION))
                .signWith(SignatureAlgorithm.HS512, secret_key)
                .compact();
    }

    public boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        final String email = extractEmail(token);

        return username.equals(user.getUsername()) && email.equals(user.getEmail()) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }
}
