package ru.gorchanyuk.securitywithjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.gorchanyuk.securitywithjwt.controller.api.AuthControllerApi;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.dto.authentication.RefreshTokenRequest;
import ru.gorchanyuk.securitywithjwt.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerApi {

    private final AuthService authenticationService;

    @Override
    public AuthenticationResponse auth(AuthenticationRequest authenticationRequestDTO) {
        return authenticationService.authentication(authenticationRequestDTO);
    }

    @Override
    public AuthenticationResponse refresh(RefreshTokenRequest request) {
        return authenticationService.refresh(request.getRefreshToken());
    }

}
