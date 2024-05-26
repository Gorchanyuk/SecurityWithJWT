package ru.gorchanyuk.securitywithjwt.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.dto.user.UserResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.exception.SuchUsernameAlreadyExistException;
import ru.gorchanyuk.securitywithjwt.mapper.UserMapper;
import ru.gorchanyuk.securitywithjwt.repository.UserRepository;
import ru.gorchanyuk.securitywithjwt.test.util.ModelGenerator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование методов класса UserServiceImpl")
public class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl service;

    @Test
    @DisplayName("Проверка успешного получения пользователя по логину")
    void testFindByUsername() {
        String username = "username";
        User user = ModelGenerator.createUser(username);
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = service.findByUsername(username);

        assertEquals(user, result);
        verify(repository, times(1)).findByUsername(anyString());

    }

    @Test
    @DisplayName("Проверка выброса исключения при отсутствии пользователя в бд")
    void testFindByUsernameFailure() {
        String username = "username";
        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.findByUsername(username));
        verify(repository, times(1)).findByUsername(anyString());
    }

    @Test
    @DisplayName("Проверка успешного сохранения пользователя в  БД")
    void testCreate() {
        String username = "username";
        User user = ModelGenerator.createUser(username);
        UserRequest request = ModelGenerator.createUserRequest();
        UserResponse response = ModelGenerator.createUserResponse();
        when(repository.findByUsername(username)).thenReturn(Optional.empty());
        when(mapper.dtoToEntity(request)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodePassword");
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.entityToOutDto(any(User.class))).thenReturn(response);

        UserResponse result = service.create(request);

        assertNotNull(result);
        verify(repository).findByUsername(username);
        verify(repository).save(any(User.class));
    }

    @Test
    @DisplayName("Проверка выброса исключения при сохранении пользователя в БД")
    void testCreateFailure() {
        String username = "username";
        User user = ModelGenerator.createUser(username);
        UserRequest request = ModelGenerator.createUserRequest();
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        assertThrows(SuchUsernameAlreadyExistException.class, () -> service.create(request));
        verify(repository).findByUsername(username);
    }
}
