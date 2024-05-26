package ru.gorchanyuk.securitywithjwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.dto.authentication.RefreshTokenRequest;
import ru.gorchanyuk.securitywithjwt.exception.InvalidPasswordException;
import ru.gorchanyuk.securitywithjwt.exception.InvalidTokenException;
import ru.gorchanyuk.securitywithjwt.exception.RefreshTokenException;
import ru.gorchanyuk.securitywithjwt.service.AuthService;
import ru.gorchanyuk.securitywithjwt.test.util.ModelGenerator;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Тестирование методов кортроллера AuthController")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка успешной аутентификации")
    void testAuth() {
        AuthenticationRequest request = ModelGenerator.createAuthRequest();
        AuthenticationResponse response = ModelGenerator.createAuthResponse();
        String jsonRequest = mapper.writeValueAsString(request);
        when(authService.authentication(any(AuthenticationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("exceptionAndExpectedStatusesForAuth")
    @DisplayName("Проверка выброса исключений при аутентификации")
    void testAuthThrowException(Exception exception, HttpStatus expectedStatus) {
        AuthenticationRequest request = ModelGenerator.createAuthRequest();
        String jsonRequest = mapper.writeValueAsString(request);
        when(authService.authentication(any(AuthenticationRequest.class))).thenThrow(exception);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().is(expectedStatus.value()));
    }

    static Stream<Arguments> exceptionAndExpectedStatusesForAuth() {
        return Stream.of(
                Arguments.of(new UsernameNotFoundException(""), HttpStatus.NOT_FOUND),
                Arguments.of(new InvalidPasswordException(), HttpStatus.UNAUTHORIZED)
        );
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка успешного обновления токенов")
    void testRefresh() {
        RefreshTokenRequest request = ModelGenerator.createRefreshTokenRequest();
        AuthenticationResponse response = ModelGenerator.createAuthResponse();
        String jsonRequest = mapper.writeValueAsString(request);
        when(authService.refresh(anyString())).thenReturn(response);

        mockMvc.perform(post("/refresh")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("exceptionAndExpectedStatusesForRefresh")
    @DisplayName("Проверка выброса исключений при обновлении токена ")
    void testRefreshThrowException(Exception exception, HttpStatus expectedStatus) {
        RefreshTokenRequest request = ModelGenerator.createRefreshTokenRequest();
        String jsonRequest = mapper.writeValueAsString(request);
        when(authService.refresh(anyString())).thenThrow(exception);

        mockMvc.perform(post("/refresh")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().is(expectedStatus.value()));
    }

    static Stream<Arguments> exceptionAndExpectedStatusesForRefresh() {
        return Stream.of(
                Arguments.of(new InvalidTokenException(), HttpStatus.NOT_FOUND),
                Arguments.of(new RefreshTokenException(), HttpStatus.UNAUTHORIZED)
        );
    }
}
