package ru.gorchanyuk.securitywithjwt.service.impl;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.props.AccessTokenProperties;
import ru.gorchanyuk.securitywithjwt.test.util.ModelGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов класса AccessTokenService")
public class AccessTokenServiceTest {

    @Mock
    private static AccessTokenProperties properties;
    @InjectMocks
    private AccessTokenService service;
    private static String secret;
    private static User user;

    @BeforeAll
    public static void setUp() {
        secret = "J4q6+pAl1FC2uV720nnXgP2Y5bVNG67m//YeoR/Q+It2WnOZZ2iIhvA94WQ1CCO7AWpdT6sywLi+EP0m8afJCw==";
        user = ModelGenerator.createUser("username");
    }

    @Test
    @DisplayName("Проверка успешной генерации токена")
    void testGenerateToken() {
        when(properties.getExpiration()).thenReturn(Duration.ofSeconds(10));
        when(properties.getSecretKey()).thenReturn(secret);

        String token = service.generateToken(user);

        assertNotNull(token);
    }

    @Test
    @DisplayName("Проверка успешной валидации токена")
    void testValidateToken() {
        String token = ModelGenerator.generateToken(user, secret);
        when(properties.getSecretKey()).thenReturn(secret);

        boolean result = service.validateToken(token);

        assertTrue(result);
    }

    @Test
    @DisplayName("Проверка неудачной валидации токена")
    void testValidateTokenFailure() {
        String token = "invalid token";
        when(properties.getSecretKey()).thenReturn(secret);

        boolean result = service.validateToken(token);

        assertFalse(result);
    }

    @Test
    @DisplayName("Проверка получения claims из токена")
    void testGetClaims() {
        String token = ModelGenerator.generateToken(user, secret);
        when(properties.getSecretKey()).thenReturn(secret);

        Claims claims = service.getClaims(token);

        assertEquals(user.getUsername(), claims.getSubject());
    }


}
