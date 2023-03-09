package com.example.diviction

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun getOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Diviction")
                    .description("진주원 여기 잠들다")
                    .version("v0.0.1")
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("External Description Here")
            )
            .components(
                Components().addSecuritySchemes(
                    "bearerAuth", SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                        .`in`(SecurityScheme.In.HEADER).name("Authorization")
                )
            )
            .security(
                listOf(
                    SecurityRequirement().addList("bearerAuth")
                )
            )
    }
}
