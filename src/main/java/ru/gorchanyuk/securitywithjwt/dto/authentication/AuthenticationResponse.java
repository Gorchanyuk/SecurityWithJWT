package ru.gorchanyuk.securitywithjwt.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Модель для передачи клиенту JWT токенов для аутентификации")
public class AuthenticationResponse {

    @Schema(description = "Токен для аутентификации", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzE2NTI4MTk4LCJleHAiOjE3MTY1MjgyNTgsImlkIjoxfQ.fA71CLW0jVfB-Oek05v8wEedBCBlK-1V8ymCq0dHgzI10o4Z6NMS3j3iFmpgfXQSWL-ApBGaMUFxqcrjfyyT_Q")
    public String accessToken;

    @Schema(description = "Токен для обновления токенов", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzE2NTI4MTk4LCJleHAiOjE3MTY1MjgyNTh9.t87xJwgemmWln7Otf6lF3ICILoGRJ4K_3TTN0rSSnUbEIrEukIqyZwCkmIOFrN_n7YXhJc-YUn9IgcTs3IY4pA")
    public String refreshToken;
}
