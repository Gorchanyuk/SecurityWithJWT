package ru.gorchanyuk.securitywithjwt.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "UserController",
        description = "Контроллер к которому может получить доступ пользователь с ролью 'USER'")
public interface UserControllerApi {

    @Operation(summary = "Доступен для пользователей у которых роль 'USER'",
            description = "Позволяет получить доступ пользователю с ролью 'USER'",
            responses = {
                    @ApiResponse(responseCode = "200")
            })
    @GetMapping(value = "/user", produces = MediaType.TEXT_PLAIN_VALUE)
    String user();
}
