package com.example.javapoc.handler;

import com.example.javapoc.entities.Project;
import com.example.javapoc.service.ProjectService;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@SpringBootTest
class ProjectHandlerTest {

    @InjectMocks
    ProjectHandler projectHandler;


    @Mock
    ProjectService projectService;

    @Mock
    ServerRequestWrapper serverRequestWrapper;


    @Test
    void getAllProjects() {
        when(projectService.getAllProjects()).thenReturn(Flux.just(new Project()));
        Mono<ServerResponse> serverResponse = projectHandler.getAllProjects(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });

    }

    @Test
    void getProjectById() {
        when(serverRequestWrapper.pathVariable("projectId")).thenReturn("projectId");
        when(projectService.getProjectById(ArgumentMatchers.anyString())).thenReturn(Mono.just(new Project()));
        Mono<ServerResponse> serverResponse = projectHandler.getProjectById(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });

    }

    @Test
    void addProject() {
        when(serverRequestWrapper.bodyToMono(Project.class)).thenReturn(Mono.just(new Project())); //dto
        when(projectService.addProject(ArgumentMatchers.any())).thenReturn(Mono.just(new Project()));
        Mono<ServerResponse> serverResponse = projectHandler.addProject(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });

    }


    @Test
    void deleteProject() {
        when(serverRequestWrapper.pathVariable("projectId")).thenReturn("projectId");
        when(projectService.deleteProject(ArgumentMatchers.anyString())).thenReturn(Mono.empty());
        Mono<ServerResponse> serverResponse = projectHandler.deleteProject(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });
    }

    @Test
    void updateProject() {
        when(serverRequestWrapper.pathVariable("projectId")).thenReturn("projectId");
        when(serverRequestWrapper.bodyToMono(Project.class)).thenReturn(Mono.just(new Project())); //DTO
        when(projectService.updateProject(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(Mono.just(new Project()));
        Mono<ServerResponse> serverResponse = projectHandler.updateProject(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });
    }

}