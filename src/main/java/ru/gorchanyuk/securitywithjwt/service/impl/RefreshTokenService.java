package ru.gorchanyuk.securitywithjwt.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.props.RefreshTokenProperties;
import ru.gorchanyuk.securitywithjwt.repository.RefreshTokenRepository;
import ru.gorchanyuk.securitywithjwt.service.TokenService;

@Service
@RequiredArgsConstructor
public class RefreshTokenService extends TokenService {

    private final RefreshTokenProperties properties;
    private final RefreshTokenRepository repository;

    @Override
    public String generateToken(User user) {
        return generateToken(user, properties);
    }

    @Override
    public boolean validateToken(String token) {
        return validateToken(token, properties.getSecretKey());
    }

    @Override
    public Claims getClaims(String token) {
        return getClaims(token, properties.getSecretKey());
    }

    public String getByValue(String username) {
        return repository.getByUsername(username);
    }

    public void save(String username, String refreshToken) {
        repository.save(username, refreshToken, properties.getExpiration());
    }
}
