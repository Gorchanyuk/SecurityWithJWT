package ru.gorchanyuk.securitywithjwt.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Тестирование методов кортроллера UserController")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @WithAnonymousUser
    @DisplayName("Тестирование получения доступа к '/user' неаворизованным пользователем")
    void testAdminWithAnonymousUser() {
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("rolesAndExpectedStatuses")
    @DisplayName("Тестирование получения доступа к '/user' для различных ролей пользователей")
    void testAdminAccessByRole(String role, HttpStatus expectedStatus) {

        Authentication auth = new UsernamePasswordAuthenticationToken("user", "", AuthorityUtils.createAuthorityList(role));
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedStatus.value()));
    }

    static Stream<Arguments> rolesAndExpectedStatuses() {
        return Stream.of(
                Arguments.of("ADMIN", HttpStatus.FORBIDDEN),
                Arguments.of("USER", HttpStatus.OK)
        );
    }
}
