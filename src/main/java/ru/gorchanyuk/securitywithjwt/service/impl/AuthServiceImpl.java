package ru.gorchanyuk.securitywithjwt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.exception.InvalidPasswordException;
import ru.gorchanyuk.securitywithjwt.exception.InvalidTokenException;
import ru.gorchanyuk.securitywithjwt.service.AuthService;
import ru.gorchanyuk.securitywithjwt.service.UserService;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authRequest) {

        User user = userService.findByUsername(authRequest.getUsername());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            log.info("Пользователь {} аутентифицирован", authRequest.getUsername());
            return AuthenticationResponse.builder()
                    .accessToken(accessTokenService.generateToken(user))
                    .refreshToken(generateAndSaveNewToken(user))
                    .build();
        }
        throw new InvalidPasswordException();
    }

    @Override
    public AuthenticationResponse refresh(String refreshToken) {

        if (!refreshTokenService.validateToken(refreshToken)) {
            throw new InvalidTokenException();
        }
        String username = refreshTokenService.getClaims(refreshToken).getSubject();
        String savedRefreshToken = refreshTokenService.getByValue(username);
        if (ObjectUtils.isEmpty(savedRefreshToken) || !savedRefreshToken.equals(refreshToken)) {
            throw new InvalidTokenException();
        }
        User user = userService.findByUsername(username);
        String newRefreshToken = generateAndSaveNewToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessTokenService.generateToken(user))
                .refreshToken(newRefreshToken)
                .build();
    }

    private String generateAndSaveNewToken(User user) {
        String refreshToken = refreshTokenService.generateToken(user);
        refreshTokenService.save(user.getUsername(), refreshToken);
        return refreshToken;
    }
}
