package com.example.javapoc.router;

import com.example.javapoc.handler.SlateHandler;
import com.example.javapoc.service.SlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;




import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


@Configuration
@EnableWebFlux
public class SlateRouter {
    @Autowired
    private SlateHandler slateHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction1()

    {

        return route().GET("/projects/{projectId}/slates", // Route to return the list of Slate
                        accept(MediaType.APPLICATION_JSON),
                        slateHandler::getSlateByDistributableUrn,
                        ops -> ops.beanClass(SlateService.class).beanMethod("getSlateByDistributableUrn")).
                build()
                .and(route().GET("/projects/{projectId}/slates/{slateId}",// Route to return a particular Slate by Slate Id
                                accept(MediaType.APPLICATION_JSON),
                                slateHandler::getSlateByManifestUrnAndDistributableUrn,
                                ops -> ops.beanClass(SlateService.class)
                                        .beanMethod("getSlateByManifestUrnAndDistributableUrn"))
                        .build()
                        .and(route().POST("/projects/{projectId}/slates",// Route to add a new Slate
                                        accept(MediaType.APPLICATION_JSON),
                                        slateHandler::addSlateByManifestUrnAndDistributableUrn,
                                        ops -> ops.beanClass(SlateService.class)
                                                .beanMethod("addSlateByManifestUrnAndDistributableUrn"))
                                .build()
                                .and(route().PUT("/projects/{projectId}/slates/{slateId}",// Route to update a particular Slate by Slate id
                                                accept(MediaType.APPLICATION_JSON),
                                                slateHandler::updateSlateByManifestUrnAndDistributableUrn,
                                                ops -> ops.beanClass(SlateService.class)
                                                        .beanMethod("updateSlateByManifestUrnAndDistributableUrn"))
                                        .build()
                                        .and(route().DELETE("/projects/{projectId}/slates/{slateId}",// Route to delete a particular Slate by id
                                                        accept(MediaType.APPLICATION_JSON),
                                                        slateHandler::deleteSlateByManifestUrnAndDistributableUrn,
                                                        ops -> ops.beanClass(SlateService.class)
                                                                .beanMethod("deleteSlateByManifestUrnAndDistributableUrn"))
                                                .build()))));







    }
}
