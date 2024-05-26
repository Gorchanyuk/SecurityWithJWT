package ru.gorchanyuk.securitywithjwt.service.impl;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.exception.InvalidPasswordException;
import ru.gorchanyuk.securitywithjwt.exception.InvalidTokenException;
import ru.gorchanyuk.securitywithjwt.service.UserService;
import ru.gorchanyuk.securitywithjwt.test.util.ModelGenerator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов класса AuthServiceImpl")
public class AuthServiceImplTest {

    @Mock
    private AccessTokenService accessTokenService;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthServiceImpl service;

    @Test
    @DisplayName("Проверка успешной аутентификации")
    void testAuthentication() {
        String username = "username";
        User user = ModelGenerator.createUser(username);
        AuthenticationRequest request = ModelGenerator.createAuthRequest();
        when(userService.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(accessTokenService.generateToken(user)).thenReturn("accessToken");
        when(refreshTokenService.generateToken(user)).thenReturn("refreshToken");

        AuthenticationResponse result = service.authentication(request);

        assertNotNull(result);
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Проверка выброса исключения при аутентификации")
    void testAuthenticationFailure() {
        String username = "username";
        User user = ModelGenerator.createUser(username);
        AuthenticationRequest request = ModelGenerator.createAuthRequest();
        when(userService.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> service.authentication(request));
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Проверка проверка выброса исключения если токен не валиден")
    void testRefreshFailure() {
        String token = "refresh token";
        when(refreshTokenService.validateToken(token)).thenReturn(false);

        assertThrows(InvalidTokenException.class, () -> service.refresh(token));
        verify(refreshTokenService).validateToken(anyString());
    }

    @Test
    @DisplayName("Проверка проверка выброса исключения если токена нет в БД")
    void testRefreshFailure2() {
        String token = "refresh token";
        Claims claims = mock(Claims.class);
        when(refreshTokenService.validateToken(token)).thenReturn(true);
        when(refreshTokenService.getClaims(token)).thenReturn(claims);
        when(claims.getSubject()).thenReturn("username");
        when(refreshTokenService.getByValue(anyString())).thenReturn(null);

        assertThrows(InvalidTokenException.class, () -> service.refresh(token));
        verify(refreshTokenService).validateToken(anyString());
        verify(refreshTokenService).getByValue(anyString());
    }

    @Test
    @DisplayName("Проверка успешного обновления токенов")
    void testRefresh() {
        String username = "username";
        String token = "refresh token";
        Claims claims = mock(Claims.class);
        String newRefreshToken = "new refresh Token";
        User user = ModelGenerator.createUser(username);
        when(refreshTokenService.validateToken(token)).thenReturn(true);
        when(refreshTokenService.getClaims(token)).thenReturn(claims);
        when(claims.getSubject()).thenReturn(username);
        when(refreshTokenService.getByValue(anyString())).thenReturn(token);
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(refreshTokenService.generateToken(user)).thenReturn(newRefreshToken);

        AuthenticationResponse result = service.refresh(token);

        assertNotNull(result);
        verify(refreshTokenService, times(1)).save(eq(username), eq(newRefreshToken));

    }
}
