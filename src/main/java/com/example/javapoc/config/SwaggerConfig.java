package com.example.javapoc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi elementApi(){
        String[] paths = new String[]{"/projects/*/slates/*/elements/**"};
        return GroupedOpenApi.builder().group("elements").pathsToMatch(paths).build();
    }

    @Bean
    public GroupedOpenApi CommentApi() {
        String[] paths = new String[]{"/elements/**"};
        return GroupedOpenApi.builder().group("comments").pathsToMatch(paths).build();
    }
        @Bean
        public GroupedOpenApi ProjectApi () {
            String[] paths = new String[]{"/projects/**"};
            return GroupedOpenApi.builder().group("project").pathsToMatch(paths).build();
        }

    @Bean
    public GroupedOpenApi SlateApi(){
        String[] paths=new String[]{"/projects/*/slates/**"};
        return GroupedOpenApi.builder().group("Slate").pathsToMatch(paths).build();
    }

    @Bean
    public GroupedOpenApi AuthenticationApi() {
        String[] paths = {"/auth/**","/users/**"};
        return GroupedOpenApi.builder().group("Authentication-Api").pathsToMatch(paths).build();
    }

}
