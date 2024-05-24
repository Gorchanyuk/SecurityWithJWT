package ru.gorchanyuk.securitywithjwt.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring Security with JWT",
                description = "Предоставляет демонстрационные методы для работы с сервисом SecurityWithJWT с использованием JWT токена",
                version = "1.0.0",
                contact = @Contact(
                        name = "Gorchanyuk"
                )
        ),
        security = {@SecurityRequirement(name = "Bearer Authentication")}
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SwaggerConfiguration {
}
