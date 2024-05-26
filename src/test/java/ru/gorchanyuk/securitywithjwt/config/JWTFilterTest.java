package ru.gorchanyuk.securitywithjwt.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gorchanyuk.securitywithjwt.security.UzerDetailsService;
import ru.gorchanyuk.securitywithjwt.service.impl.AccessTokenService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов класса JWTFilter")
public class JWTFilterTest {


    @InjectMocks
    private JWTFilter jwtFilter;
    @Mock
    private AccessTokenService tokenService;
    @Mock
    private UzerDetailsService uzerDetailsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @SneakyThrows
    @Test
    @DisplayName("Проверка прохождения запроса без токена")
    public void testDoFilterInternal_NoToken() {
        when(request.getHeader(anyString())).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка прохождения запроса с недействительным токеном")
    public void testDoFilterInternal_InvalidToken() {
        String invalidToken = "Bearer invalid_token";
        when(request.getHeader(anyString())).thenReturn(invalidToken);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(tokenService).validateToken(anyString());
    }

    @SneakyThrows
    @Test
    @DisplayName("Проверка аутентификации пользователя с действительным токеном")
    public void testDoFilterInternal_ValidToken() {
        String validToken = "Bearer valid_token";
        Claims claims = mock(Claims.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(request.getHeader("Authorization")).thenReturn(validToken);
        when(tokenService.validateToken(anyString())).thenReturn(true);
        when(tokenService.getClaims(anyString())).thenReturn(claims);
        when(claims.getSubject()).thenReturn("username");
        when(uzerDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }
}