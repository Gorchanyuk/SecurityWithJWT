package ru.gorchanyuk.securitywithjwt.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.gorchanyuk.securitywithjwt.util.RoleUser;

import java.util.Set;

@Data
@Builder
@Schema(description = "Модель пользователя для передачи данных на сервер")
public class UserRequest {

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @Schema(description = "Учетная запись", example = "ivan_123")
    private String username;

    @Schema(description = "Пароль", example = "password")
    private String password;

    @Schema(description = "Роли пользователя, может быть ADMIN или USER", example = "[\"ADMIN\", \"USER\"]")
    private Set<RoleUser> roles;
}