package ru.gorchanyuk.securitywithjwt.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.dto.error.ErrorResponse;
import ru.gorchanyuk.securitywithjwt.dto.user.UserRequest;

@SecurityRequirements
@Tag(name = "RegistrationController", description = "Контроллер для регистрации пользователей в системе")
public interface RegistrationControllerApi {

    @Operation(summary = "Регистрация",
            description = "Возвращает JWT токен для аутентификации в системе",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/registration",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationResponse registration(@RequestBody UserRequest user);
}
