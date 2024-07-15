package ru.gorchanyuk.securitywithjwt.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Модель для отправки токена на обновление")
public class RefreshTokenRequest {

    @Schema(description = "Токен обновления")
    private String refreshToken;
}