package ru.gorchanyuk.securitywithjwt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gorchanyuk.securitywithjwt.dto.error.ErrorResponse;
import ru.gorchanyuk.securitywithjwt.exception.InvalidPasswordException;
import ru.gorchanyuk.securitywithjwt.exception.InvalidTokenException;
import ru.gorchanyuk.securitywithjwt.exception.RefreshTokenException;
import ru.gorchanyuk.securitywithjwt.exception.SuchUsernameAlreadyExistException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(UsernameNotFoundException e) {

        String message = "Поьзователь с таким логином не найден";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(InvalidPasswordException e) {

        String message = "Неверный пароль";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(InvalidTokenException e) {

        String message = "Токен аутентификации не действителен";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(RefreshTokenException e) {

        String message = "Ошибка токена обновления";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(SuchUsernameAlreadyExistException e) {

        String message = "Пользователь с таким username уже существует";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    private ErrorResponse getErrorResponse(String message, Exception e) {
        log.warn(message + " Сообщение ошибки: {}", e.getMessage());
        return ErrorResponse.builder()
                .message(message)
                .reason(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
