package ru.gorchanyuk.securitywithjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.gorchanyuk.securitywithjwt.controller.api.AdminControllerApi;

@RestController

public class AdminController implements AdminControllerApi {

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String admin() {

        return "К этой странице доступ есть у пользователей с ролью 'ADMIN'";
    }
}
