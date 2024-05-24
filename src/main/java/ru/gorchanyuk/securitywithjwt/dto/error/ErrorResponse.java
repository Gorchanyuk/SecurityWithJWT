package ru.gorchanyuk.securitywithjwt.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

//Класс для представления ошибки
@Data
@Builder
@Schema(description = "Модель для передачи сообщения об ошибке")
public class ErrorResponse {

    @Schema(description = "Информация об ошибке")
    private String message;

    @Schema(description = "Описание причины ошибки")
    private String reason;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "Дата и время когда произошла ошибка")
    private LocalDateTime timestamp;


}