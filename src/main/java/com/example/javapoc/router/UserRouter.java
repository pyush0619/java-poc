package com.example.javapoc.router;

import com.example.javapoc.handler.AuthHandler;
import com.example.javapoc.handler.UserHandler;
import com.example.javapoc.repositories.UserRepository;
import com.example.javapoc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@EnableWebFlux
public class UserRouter implements WebFluxConfigurer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthHandler authHandler;

    @Bean
    public RouterFunction<ServerResponse> userRoute() {
        UserHandler userHandler = new UserHandler(userRepository);
        return RouterFunctions
                .route(POST("/users").and(accept(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET("/users").and(accept(APPLICATION_JSON)), userHandler::listUser)
                .andRoute(GET("/users/{userId}").and(accept(APPLICATION_JSON)), userHandler::getUserById)
                .andRoute(DELETE("/users/{userId}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
    }

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
/*
        return RouterFunctions
                .route(POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login)
                .andRoute(POST("/auth/signup").and(accept(APPLICATION_JSON)), authHandler::signUp);
*/
        return route()
                .POST("/auth/login",
                        accept(MediaType.APPLICATION_JSON),
                        authHandler::login,
                        ops -> ops.beanClass(AuthHandler.class).beanMethod("login"))
                .build()
                .and(route()
                .POST("/auth/signup",
                        accept(MediaType.APPLICATION_JSON),
                        authHandler::signUp,
                        ops -> ops.beanClass(AuthHandler.class).beanMethod("signUp"))
                .build());

    }

}
