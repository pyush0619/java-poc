package com.example.javapoc.service;

import com.example.javapoc.entities.Project;
import com.example.javapoc.repositories.ProjectRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;
    @Mock
    private ProjectRepo projectRepo;

    String distributableUrn="urn:distributable:"+ UUID.randomUUID().toString();
    String entityUrn="urn:entity:"+UUID.randomUUID().toString();
    Instant createdTimestamp = Instant.now();
    Instant updatedTimestamp = Instant.now();

    @Test
    void getProjectById() {
        Project project = new Project("distributableUrn", "entityURN", "My Project", "Rick Martin", "pyush", createdTimestamp, "pyush", updatedTimestamp);
        Mockito.when(projectRepo.findById(distributableUrn)).thenReturn(Mono.just(project));
        Mono<Project> projectMono = projectService.getProjectById(distributableUrn);
        StepVerifier.create(projectMono).consumeNextWith(newProject -> {
            assertEquals("distributableUrn", newProject.getDistributableUrn());
        }).verifyComplete();
    }

    @Test
    void getAllProjects() {
        Project project = new Project("distributableUrn", "entityURN", "My Project", "Rick Martin", "pyush", createdTimestamp, "pyush", updatedTimestamp);
        Mockito.when(projectRepo.findAll()).thenReturn(Flux.just(project));
        Flux<Project> projectFlux = projectService.getAllProjects();
        StepVerifier.create(projectFlux).expectSubscription()
                .expectNext(new Project("distributableUrn", "entityURN", "My Project", "Rick Martin", "pyush", createdTimestamp, "pyush", updatedTimestamp))
                .verifyComplete();
    }

    @Test
    void addProject() {
        Project project = new Project("distributableUrn", "entityURN", "My Project", "Rick Martin", "pyush", createdTimestamp, "pyush", updatedTimestamp);
        Project projectDTO = new Project("distributableUrn", "entityURN", "My Project", "Rick Martin", "pyush", createdTimestamp, "pyush", updatedTimestamp);
        Mockito.when(projectRepo.save(any(Project.class))).thenReturn(Mono.just(project));
        Mono<Project> projectMono = projectService.addProject(projectDTO);
        projectMono.subscribe(response -> {
            assertTrue(response.getTitle().contains("My Project"));
        });
    }

    @Test
    void deleteProject() {
        Mockito.when(projectRepo.deleteById(distributableUrn)).thenReturn(Mono.empty());
        Mono<Void> projectMono = projectService.deleteProject(distributableUrn);
        StepVerifier.create(projectMono).expectComplete().verify();

    }

    @Test
    void updateProject() {
        Project project = new Project("distributableUrn", "entityURN", "My Project", "Rick Martin", "pyush", createdTimestamp, "pyush", updatedTimestamp);
        Project projectDTO= new Project("distributableUrn", "entityURN", "My Project1", "Rick Martin", "pyush", createdTimestamp, "piyush", updatedTimestamp);
        Mockito.when(projectRepo.findById("distributableUrn")).thenReturn(Mono.just(project));
        Mockito.when(projectRepo.save(ArgumentMatchers.any(Project.class))).thenReturn(Mono.just(project));
        Mono<Project> resultMono = projectService.updateProject("distributableUrn", projectDTO);
        StepVerifier.create(resultMono).expectNextMatches(res -> res.getTitle().contains("My Project"))
                .verifyComplete();

    }
}