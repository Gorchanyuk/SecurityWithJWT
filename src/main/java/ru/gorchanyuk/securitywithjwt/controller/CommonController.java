package ru.gorchanyuk.securitywithjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.gorchanyuk.securitywithjwt.controller.api.CommonControllerApi;

@RestController
public class CommonController implements CommonControllerApi {

    @Override
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    public String common() {
        return "К этой странице доступ есть у пользователей с ролью 'USER' или 'ADMIN'";
    }
}

