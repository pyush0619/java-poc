/*
package com.example.javapoc.service;

import com.example.javapoc.dto.ApiResponse;
import com.example.javapoc.dto.LoginRequest;
import com.example.javapoc.dto.LoginResponse;
import com.example.javapoc.entities.Project;
import com.example.javapoc.exception.ProjectNotFoundException;
import com.example.javapoc.handler.TokenProvider;
import com.example.javapoc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class AuthService {

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return loginRequest.flatMap(login -> userRepository.findByUsername(login.getUsername())
                .flatMap(user -> {
                    if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                        return ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromObject(new LoginResponse(tokenProvider.generateToken(user))));
                    } else {
                        return new ApiResponse(400, "Invalid credentials", null);
                    }
                }).switchIfEmpty(new ApiResponse(400, "User does not exist", null)))))
    }


    public Mono<Project> addProject(Project project) {
        String distributableUrnToAdd="urn:distributable:"+ UUID.randomUUID();
        String entityUrnToAdd="urn:entity:"+ UUID.randomUUID();
        project.setDistributableUrn(distributableUrnToAdd);
        project.setEntityUrn(entityUrnToAdd);
        project.setCreatedTimestamp(Instant.now());
        project.setUpdatedTimestamp(Instant.now());
        return projectRepo.save(project);
    }

}
*/
