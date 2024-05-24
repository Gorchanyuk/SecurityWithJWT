package ru.gorchanyuk.securitywithjwt.service;

import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;

public interface AuthService {

    AuthenticationResponse authentication(AuthenticationRequest authenticationRequestDTO);

    AuthenticationResponse refresh(String refreshToken);
}
