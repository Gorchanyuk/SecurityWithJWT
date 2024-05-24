package ru.gorchanyuk.securitywithjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.gorchanyuk.securitywithjwt.controller.api.UserControllerApi;

@RestController
public class UserController implements UserControllerApi {

    @Override
    @PreAuthorize(value = "hasAuthority('USER')")
    public String user() {

        return "К этой странице доступ есть у пользователей с ролью 'USER'";
    }
}
