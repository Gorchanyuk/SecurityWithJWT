package ru.gorchanyuk.securitywithjwt.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "CommonController",
        description = "Контроллер к которому может получить доступ пользователь с ролью USER или ADMIN")
public interface CommonControllerApi {

    @Operation(summary = "Доступен для любого аутентифицированного пользователя",
            description = "Позволяет получить доступ пользователю с любой ролью",
            responses = {
                    @ApiResponse(responseCode = "200")
            })
    @GetMapping(value = "/common", produces = MediaType.TEXT_PLAIN_VALUE)
    String common();
}
