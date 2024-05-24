package ru.gorchanyuk.securitywithjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.props.TokenProperties;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

public abstract class TokenService {

    public abstract String generateToken(User user);

    public abstract boolean validateToken(String token);

    public abstract Claims getClaims(String token);

    protected SecretKey getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    protected String generateToken(User user, TokenProperties properties) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(getExpiration(properties.getExpiration()))
                .signWith(getSigningKey(properties.getSecretKey()))
                .compact();
    }

    private Date getExpiration(Duration duration) {
        long now = new Date().getTime();
        return new Date((now + duration.toMillis()));
    }

    protected boolean validateToken(String token, String secret) {
        SecretKey secretKey = getSigningKey(secret);
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    protected Claims getClaims(String token, String secret) {
        SecretKey secretKey = getSigningKey(secret);
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
