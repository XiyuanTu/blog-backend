package com.txy.blog.security;

import com.txy.blog.exception.BlogAPIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-milliseconds}")
    private long expirationInMilliseconds;

    public String generateToken(Authentication authentication) {
        Date currentMoment = new Date();
        Date expirationMoment = new Date(currentMoment.getTime() + expirationInMilliseconds);

        String username = authentication.getName();

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationMoment)
                .signWith(generateKey())
                .compact();

        return token;
    }

    public String getUsername(String token) {
        Claims jwtBody = Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = jwtBody.getSubject();
        return username;
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(generateKey()).build().parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BlogAPIException("Invalid JWT token");
        } catch (MalformedJwtException e) {
            throw new BlogAPIException("Expired JWT token");
        } catch (SignatureException e) {
            throw new BlogAPIException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new BlogAPIException("JWT claims string is empty");
        }
    }

    private Key generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
