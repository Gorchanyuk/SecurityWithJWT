package ru.gorchanyuk.securitywithjwt.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleUser implements GrantedAuthority {

    ADMIN("ADMIN"),          //Администратор
    USER("USER")             //Пользовтель
    ;
    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
