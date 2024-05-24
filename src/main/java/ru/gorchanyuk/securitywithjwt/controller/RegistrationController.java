package ru.gorchanyuk.securitywithjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.gorchanyuk.securitywithjwt.controller.api.RegistrationControllerApi;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.service.AuthService;
import ru.gorchanyuk.securitywithjwt.service.UserService;

@RestController
@RequiredArgsConstructor
public class RegistrationController implements RegistrationControllerApi {

    private final AuthService authenticationService;
    private final UserService userService;

    @Override
    public AuthenticationResponse registration(UserRequest user) {
        userService.create(user);

        AuthenticationRequest auth = AuthenticationRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        return authenticationService.authentication(auth);
    }
}
