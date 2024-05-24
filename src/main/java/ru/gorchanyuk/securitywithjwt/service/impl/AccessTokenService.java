package ru.gorchanyuk.securitywithjwt.service.impl;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.props.AccessTokenProperties;
import ru.gorchanyuk.securitywithjwt.service.TokenService;

@Service
@RequiredArgsConstructor
public class AccessTokenService extends TokenService {

    private final AccessTokenProperties properties;

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
}
