package ru.gorchanyuk.securitywithjwt.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("Spring Security with JWT")
                        .description("Предоставляет демонстрационные методы для работы с сервисом SecurityWithJWT с использованием JWT токена")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Gorchanyuk")
                                .email("gorchanuk@gmail.com")));
    }
}
