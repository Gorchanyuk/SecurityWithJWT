package ru.gorchanyuk.securitywithjwt.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token.access")
public class AccessTokenProperties implements TokenProperties{

    private String secretKey;
    private Duration expiration;
}
