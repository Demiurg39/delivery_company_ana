package com.anateam.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Delivery Service API",
        version = "1.0",
        description = "API documentation for the AnaTeam Delivery Application",
        contact = @Contact(name = "AnaTeam Support", email = "support@anateam.com")
    )
)
@SecurityScheme(
    name = "bearerAuth",            // Это имя мы используем в контроллерах
    description = "JWT auth description",
    scheme = "bearer",              // Тип схемы
    type = SecuritySchemeType.HTTP, // HTTP авторизация
    bearerFormat = "JWT",           // Формат токена
    in = SecuritySchemeIn.HEADER    // Передается в заголовке
)
public class OpenAPIConfig {}
