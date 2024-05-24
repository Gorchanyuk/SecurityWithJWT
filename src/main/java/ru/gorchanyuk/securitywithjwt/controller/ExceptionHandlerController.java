package ru.gorchanyuk.securitywithjwt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gorchanyuk.securitywithjwt.dto.error.ErrorResponse;
import ru.gorchanyuk.securitywithjwt.exception.*;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityDoesNotExistException e) {

        String message = "Пользоваетель не найден";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException e) {

        String message = "Поьзователь с таким логином не найден";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(LockedException e) {

        String message = "Учетная запись заблокирована";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {

        String message = "Недействительные учетные данные";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(DisabledException e) {

        String message = "Учетная запись отключена";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(InvalidPasswordException e) {

        String message = "Неверный пароль";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(InvalidTokenException e) {

        String message = "Токен аутентификации не действителен";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RefreshTokenException e) {

        String message = "Ошибка токена обновления";
        ErrorResponse response = getErrorResponse(message, e);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SuchUsernameAlreadyExistException e) {
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
