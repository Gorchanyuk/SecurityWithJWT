package ru.gorchanyuk.securitywithjwt.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.props.RefreshTokenProperties;
import ru.gorchanyuk.securitywithjwt.repository.RefreshTokenRepository;
import ru.gorchanyuk.securitywithjwt.test.util.ModelGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов класса RefreshTokenService")
public class RefreshTokenServiceTest {

    @Mock
    private static RefreshTokenProperties properties;
    @Mock
    private RefreshTokenRepository repository;
    @InjectMocks
    private RefreshTokenService service;
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
    @DisplayName("Проверка получения токена из БД")
    void testGetByValue() {
        String token = "token";
        when(repository.getByUsername(user.getUsername())).thenReturn(token);

        String result = service.getByValue(user.getUsername());

        assertEquals(token, result);
    }

    @Test
    @DisplayName("Проверка сохранения токена в БД")
    void testSave() {

        String token = "token";
        doNothing().when(repository).save(anyString(), anyString(), any(Duration.class));

        service.save(user.getUsername(), token);

        verify(repository, times(1)).save(anyString(), anyString(), any(Duration.class));
    }
}
