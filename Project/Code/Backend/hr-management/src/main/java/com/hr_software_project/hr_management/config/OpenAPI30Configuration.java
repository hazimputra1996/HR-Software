package com.hr_software_project.hr_management.config;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class OpenAPI30Configuration {

    // main configuration for openAPI, configure to use bearer token
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("PMA API")
                        .description("Congratulations! We have upgraded to openAPI 3.0!!! Now we suffer with this new UI")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    // configure UI to collapse all modules on default
    @Bean
    public SwaggerUiConfigParameters swaggerUiConfigParameters() {
        SwaggerUiConfigProperties properties = new SwaggerUiConfigProperties();
        properties.setDocExpansion("NONE"); // default collapse all tags
        properties.setTagsSorter("alpha"); // sort tags alphabetically
        properties.setDefaultModelExpandDepth(99); // expand fully all models/schema
        properties.setDefaultModelRendering("false");

        SwaggerUiConfigParameters parameters = new SwaggerUiConfigParameters(properties);
        return parameters;
    }

    // since actuator info will read build-info.properties and serialize the time to json, it has issues
    // hence include this for proper datetime conversion.
    // REMOVE IF CAUSING ISSUES WITH SERIALIZATION AS THIS OVERWRITES DEFAULT OBJECTMAPPER FOR JSON
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modules(new JavaTimeModule());
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.simpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return builder;
    }

}
