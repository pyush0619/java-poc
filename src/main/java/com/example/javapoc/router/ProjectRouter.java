package com.example.javapoc.router;

import com.example.javapoc.handler.ProjectHandler;
import com.example.javapoc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@EnableWebFlux
public class ProjectRouter implements WebFluxConfigurer {
    @Autowired
    private ProjectHandler projectHandler;

    @Bean
    public RouterFunction<ServerResponse> routerProject() {
        return route()
                .GET("/projects",
                        accept(MediaType.APPLICATION_JSON),
                        projectHandler::getAllProjects,
                        ops -> ops.beanClass(ProjectService.class).beanMethod("getAllProjects"))
                .build()
                .and(route()
                        .GET("/projects/{projectId}",
                                accept(MediaType.APPLICATION_JSON),
                                projectHandler::getProjectById,
                                ops -> ops.beanClass(ProjectService.class).beanMethod("getProjectById"))
                        .build()
                        .and(route()
                                .POST("/projects",
                                        accept(MediaType.APPLICATION_JSON),
                                        projectHandler::addProject,
                                        ops -> ops.beanClass(ProjectService.class).beanMethod("addProject"))
                                .build()
                                .and(route()
                                        .DELETE("/projects/{projectId}",
                                                accept(MediaType.APPLICATION_JSON),
                                                projectHandler::deleteProject,
                                                ops -> ops.beanClass(ProjectService.class).beanMethod("deleteProject"))
                                        .build()
                                        .and(route()
                                                .PUT("/projects/{projectId}",
                                                        accept(MediaType.APPLICATION_JSON),
                                                        projectHandler::updateProject,
                                                        ops -> ops.beanClass(ProjectService.class).beanMethod("updateProject"))
                                                .build()
                                        )
                                )
                        )
                );
    }
}
