package ru.gorchanyuk.securitywithjwt.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gorchanyuk.securitywithjwt.entity.User;
import ru.gorchanyuk.securitywithjwt.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class UzerDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Загрузка пользователя '{}' для аутентификации", username);
        User user = userService.findByUsername(username);

        return new UzerDetails(user);
    }
}