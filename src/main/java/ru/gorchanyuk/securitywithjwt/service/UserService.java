package ru.gorchanyuk.securitywithjwt.service;


import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.dto.user.UserResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;

public interface UserService {

    User findByUsername(String username);

    UserResponse create(UserRequest userRequest);
}

