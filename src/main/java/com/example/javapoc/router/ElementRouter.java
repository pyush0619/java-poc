package com.example.javapoc.router;
import com.example.javapoc.handler.ElementHandler;
import com.example.javapoc.service.ElementService;
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
public class ElementRouter implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> routerElement(ElementHandler elementHandler) {
        return route()
                .GET("/projects/{projectId}/slates/{slateId}/elements",
                        accept(MediaType.APPLICATION_JSON),
                        elementHandler::getAllElements,
                        ops -> ops.beanClass(ElementService.class).beanMethod("getAllElements"))
                .build()
                .and(route()
                        .GET("/projects/{projectId}/slates/{slateId}/elements/{elementId}",
                                accept(MediaType.APPLICATION_JSON),
                                elementHandler::getElementBySlateManifestUrnAndWorkUrn,
                                ops -> ops.beanClass(ElementService.class).beanMethod("getElementBySlateManifestUrnAndWorkUrn"))
                        .build()
                        .and(route()
                                .POST("/projects/{projectId}/slates/{slateId}/elements",
                                        accept(MediaType.APPLICATION_JSON),
                                        elementHandler::addElement,
                                        ops -> ops.beanClass(ElementService.class).beanMethod("addElement"))
                                .build()
                                .and(route()
                                        .DELETE("/projects/{projectId}/slates/{slateId}/elements/{elementId}",
                                                accept(MediaType.APPLICATION_JSON),
                                                elementHandler::deleteElement,
                                                ops -> ops.beanClass(ElementService.class).beanMethod("deleteElement"))
                                        .build()
                                        .and(route()
                                                .PUT("/projects/{projectId}/slates/{slateId}/elements/{elementId}",
                                                        accept(MediaType.APPLICATION_JSON),
                                                        elementHandler::updateElement,
                                                        ops -> ops.beanClass(ElementService.class).beanMethod("updateElement"))
                                                .build()
                                        )
                                )
                        )
                );
    }
}
