package ru.gorchanyuk.securitywithjwt.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Модель для аутентификации в системе")
public class AuthenticationRequest {

    @Schema(description = "Учетная запись", example = "superuser")
    private String username;

    @Schema(description = "Пароль", example = "password")
    private String password;
}
