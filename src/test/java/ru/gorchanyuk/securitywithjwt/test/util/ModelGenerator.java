package ru.gorchanyuk.securitywithjwt.test.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.dto.authentication.RefreshTokenRequest;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;
import ru.gorchanyuk.securitywithjwt.dto.user.UserResponse;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.util.RoleUser;

import java.util.Date;
import java.util.Set;

public class ModelGenerator {

    public static User createUser(String username) {
        return User.builder()
                .id(1L)
                .username(username)
                .roles(Set.of(RoleUser.USER))
                .password("password")
                .build();
    }

    public static UserRequest createUserRequest() {
        return UserRequest.builder()
                .username("username")
                .firstName("name")
                .lastName("lastName")
                .password("password")
                .roles(Set.of(RoleUser.USER))
                .build();
    }

    public static UserResponse createUserResponse() {
        return UserResponse.builder()
                .username("username")
                .firstName("name")
                .lastName("lastName")
                .roles(Set.of(RoleUser.USER))
                .build();
    }

    public static String generateToken(User user, String secret) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();
    }

    public static AuthenticationRequest createAuthRequest() {
        return AuthenticationRequest.builder()
                .username("username")
                .password("password")
                .build();
    }

    public static AuthenticationResponse createAuthResponse() {
        return AuthenticationResponse.builder()
                .accessToken("access token")
                .refreshToken("refresh token")
                .build();
    }

    public static RefreshTokenRequest createRefreshTokenRequest() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("refresh token");
        return request;
    }
}
