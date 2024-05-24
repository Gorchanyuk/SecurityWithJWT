package ru.gorchanyuk.securitywithjwt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.dto.user.UserResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.exception.SuchUsernameAlreadyExistException;
import ru.gorchanyuk.securitywithjwt.mapper.UserMapper;
import ru.gorchanyuk.securitywithjwt.repository.UserRepository;
import ru.gorchanyuk.securitywithjwt.service.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь сучетной записью: " + username + " не найден"));
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        Optional<User> userOption = userRepository.findByUsername(userRequest.getUsername());
        if (userOption.isPresent()) {
            throw new SuchUsernameAlreadyExistException();
        }
        User user = mapper.dtoToEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return mapper.entityToOutDto(user);
    }
}
