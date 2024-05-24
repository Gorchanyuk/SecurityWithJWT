package ru.gorchanyuk.securitywithjwt.props;

import java.time.Duration;

public interface TokenProperties {

    Duration getExpiration();

    String getSecretKey();
}
