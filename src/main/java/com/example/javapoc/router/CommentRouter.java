package com.example.javapoc.router;

import com.example.javapoc.entities.Comment;
import com.example.javapoc.handler.CommentHandler;
import com.example.javapoc.service.CommentService;
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
public class CommentRouter implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> routerComment(CommentHandler commentHandler){
        return  route().GET("/elements/{elementId}",
                accept(MediaType.APPLICATION_JSON),
                commentHandler::getCommentsById,
                ops->ops.beanClass(CommentService.class).beanMethod("getCommentsByID"))
                .build()
                .and(route().POST("/elements/{elementId}",
                        accept(MediaType.APPLICATION_JSON),
                        commentHandler::addComment,
                        ops->ops.beanClass(CommentService.class).beanMethod("addComment")).build()
                        .and(route().PUT("/elements/{elementId}/comments/{commentId}",
                                accept(MediaType.APPLICATION_JSON),
                                commentHandler::updateComment,
                                ops->ops.beanClass(CommentService.class).beanMethod("updateComment")).build()
                                .and(route().DELETE("/elements/{elementId}/comments/{commentId}",
                                        accept(MediaType.APPLICATION_JSON),
                                        commentHandler::deleteComment,
                                        ops->ops.beanClass(CommentService.class).beanMethod("deleteComment")).build()
                                        .and(route().GET("/elements/{elementId}/comments/{commentId}",
                                                accept(MediaType.APPLICATION_JSON),
                                                commentHandler::getRepliesById,
                                                ops->ops.beanClass(CommentService.class).beanMethod("getRepliesById")).build()
                                                .and(route().POST("/elements/{elementId}/comments/{commentId}",
                                                        accept(MediaType.APPLICATION_JSON),
                                                        commentHandler::addReply,
                                                        ops->ops.beanClass(CommentService.class).beanMethod("addReply")).build()
                                                        .and(route().PUT("/elements/{elementId}/comments/{commentId}/replies/{replyId}",
                                                                accept(MediaType.APPLICATION_JSON),
                                                                commentHandler::updateReply,
                                                                ops-> ops.beanClass(CommentService.class).beanMethod("updateReply")).build()
                                                                .and(route().DELETE("/elements/{elementId}/comments/{commentId}/replies/{replyId}",
                                                                        accept(MediaType.APPLICATION_JSON),
                                                                        commentHandler::deleteReply,
                                                                        ops-> ops.beanClass(CommentService.class).beanMethod("deleteReply")).build())))))));
    }
}
