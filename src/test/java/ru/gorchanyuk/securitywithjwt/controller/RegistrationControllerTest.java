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
import org.springframework.test.web.servlet.MockMvc;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.exception.SuchUsernameAlreadyExistException;
import ru.gorchanyuk.securitywithjwt.service.AuthService;
import ru.gorchanyuk.securitywithjwt.service.UserService;
import ru.gorchanyuk.securitywithjwt.test.util.ModelGenerator;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Тестирование методов кортроллера RegistrationController")
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private UserService userService;
    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка успешной регистрации")
    void testRegistration() {
        UserRequest request = ModelGenerator.createUserRequest();
        AuthenticationResponse response = ModelGenerator.createAuthResponse();
        String jsonRequest = mapper.writeValueAsString(request);
        when(authService.authentication(any(AuthenticationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));

        verify(userService).create(request);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("exceptionAndExpectedStatuses")
    @DisplayName("Проверка выброса исключений при регистрации")
    void testAuthThrowException(Exception exception, HttpStatus expectedStatus) {
        UserRequest request = ModelGenerator.createUserRequest();
        String jsonRequest = mapper.writeValueAsString(request);
        when(authService.authentication(any(AuthenticationRequest.class))).thenThrow(exception);

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().is(expectedStatus.value()));
    }

    static Stream<Arguments> exceptionAndExpectedStatuses() {
        return Stream.of(
                Arguments.of(new SuchUsernameAlreadyExistException(), HttpStatus.FORBIDDEN)
        );
    }
}
