package ru.gorchanyuk.securitywithjwt.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.gorchanyuk.securitywithjwt.util.RoleUser;

import java.util.Set;

@Data
@Builder
@Schema(description = "Модель сотрудника для передачи данных на внешний интерфейс")
public class UserResponse {

    @Schema(description = "Уникальный идентификатор id", example = "1")
    private long id;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @Schema(description = "Учетная запись", example = "ivan_123")
    private String username;

    @Schema(description = "Роли пользователя", example = "{USER, ADMIN}")
    private Set<RoleUser> roles;

}