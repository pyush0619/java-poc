package com.example.javapoc.service;

import com.example.javapoc.exception.ProjectNotFoundException;
import com.example.javapoc.entities.Project;
import com.example.javapoc.repositories.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    /**
     * Get Comments for give projectId
     *
     * @param id
     * @return
     */

    public Mono<Project> getProjectById(@PathVariable("projectId") String id) {
        return projectRepo.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("There is no project with urn: " + id)));
    }

    public Flux<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Mono<Project> addProject(Project project) {
        String distributableUrnToAdd="urn:distributable:"+ UUID.randomUUID().toString();
        String entityUrnToAdd="urn:entity:"+UUID.randomUUID().toString();
        project.setDistributableUrn(distributableUrnToAdd);
        project.setEntityUrn(entityUrnToAdd);
        project.setCreatedTimestamp(Instant.now());
        project.setUpdatedTimestamp(Instant.now());
        return projectRepo.save(project);
    }

    public Mono<Void> deleteProject(@PathVariable ("projectId") String id) {
        return projectRepo.deleteById(id);
    }

    public Mono<Project> updateProject(@PathVariable ("projectId") String id,Project project) {
        return projectRepo.findById(id)
                .flatMap(p -> {
                    p.setTitle(project.getTitle());
                    p.setAuthor(project.getAuthor());
                    p.setUpdatedBy(project.getUpdatedBy());
                    p.setUpdatedTimestamp(Instant.now());
                    return projectRepo.save(p);
                });
    }
}
