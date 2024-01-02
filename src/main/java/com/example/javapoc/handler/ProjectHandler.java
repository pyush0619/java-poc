package com.example.javapoc.handler;

import com.example.javapoc.entities.Project;
import com.example.javapoc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.OK;

@Component
public class ProjectHandler {

    @Autowired
    ProjectService projectService;

    /**
     * handler function for getting all comments icons for an element
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getAllProjects(ServerRequest serverRequest) {
        Flux<Project> AllProject =projectService.getAllProjects();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(AllProject, Project.class);
    }

    public Mono<ServerResponse> getProjectById(ServerRequest serverRequest) {
        String id= serverRequest.pathVariable("projectId");
        Mono<Project> projectsMono=projectService.getProjectById(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(projectsMono, Project.class);
    }
    public Mono<ServerResponse> addProject(ServerRequest serverRequest){
        Mono<Project> newProject=serverRequest.bodyToMono(Project.class);
        return newProject.flatMap(p ->
                ServerResponse.status(OK).contentType(MediaType.APPLICATION_JSON)
                        .body(projectService.addProject(p), Project.class));
    }

    public Mono<ServerResponse> deleteProject(ServerRequest serverRequest){
        String id= serverRequest.pathVariable("projectId");
        Mono<Void> projectsMono=projectService.deleteProject(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectsMono, Project.class);
    }

    public Mono<ServerResponse> updateProject(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("projectId");
        Mono<Project> projectsMono = serverRequest.bodyToMono(Project.class);
        return projectsMono.flatMap(p ->
                ServerResponse.status(OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(projectService.updateProject(id,p), Project.class));
    }
}
