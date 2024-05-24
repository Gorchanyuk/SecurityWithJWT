package ru.gorchanyuk.securitywithjwt.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String msg) {
        super(msg);
    }
}