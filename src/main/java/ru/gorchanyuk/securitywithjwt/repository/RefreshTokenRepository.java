package ru.gorchanyuk.securitywithjwt.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.gorchanyuk.securitywithjwt.exception.RefreshTokenException;

import java.time.Duration;

@Slf4j
@Repository
public class RefreshTokenRepository {

    private final ValueOperations<String, String> valueOps;

    @Autowired
    public RefreshTokenRepository(StringRedisTemplate stringRedisTemplate) {
        this.valueOps = stringRedisTemplate.opsForValue();
    }

    public void save(String username, String refreshToken, Duration expTimeMillis) {
        try {
            valueOps.set(username, refreshToken, expTimeMillis);
        } catch (Exception e) {
            log.error("Ошибка сохранения токена обновления: ", e);
            throw new RefreshTokenException("Ошибка сохранения токена обновления: " + refreshToken);
        }
    }

    public String getByUsername(String username) {
        String token = valueOps.get(username);
        if (!StringUtils.hasLength(token)) {
            throw new RefreshTokenException("Токен обновления пользоваетеля: " + username + " не найден:");
        }
        return token;
    }
}