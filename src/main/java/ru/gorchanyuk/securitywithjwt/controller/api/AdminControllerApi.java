package ru.gorchanyuk.securitywithjwt.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "AdminController",
        description = "Контроллер к которому может получить доступ пользователь с ролью 'ADMIN'")
public interface AdminControllerApi {

    @Operation(summary = "Доступен для пользователей у которых роль 'ADMIN'",
            description = "Позволяет получить доступ пользователю с ролью 'ADMIN'",
            responses = {
                    @ApiResponse(responseCode = "200")
            })
    @GetMapping(value = "/admin", produces = MediaType.TEXT_PLAIN_VALUE)
    String admin();
}
