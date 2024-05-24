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
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationRequest;
import ru.gorchanyuk.securitywithjwt.dto.authentication.AuthenticationResponse;
import ru.gorchanyuk.securitywithjwt.dto.authentication.RefreshTokenRequest;
import ru.gorchanyuk.securitywithjwt.dto.error.ErrorResponse;

@SecurityRequirements
@Tag(name = "AuthController", description = "Контроллер для аутентификации в системе")
public interface AuthControllerApi {

    @Operation(summary = "Аутентификация",
            description = "Возвращает набор JWT токенов для аутентификации в системе и обновления токенов",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationResponse auth(@RequestBody AuthenticationRequest authenticationRequestDTO);

    @Operation(summary = "Обновление токенов",
            description = "Обновляет набор JWT токенов для аутентификации в системе и обновления токенов",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "Схема ошибки одинакова для всех типов ошибок",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/refresh",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    AuthenticationResponse refresh(@RequestBody RefreshTokenRequest request);
}
