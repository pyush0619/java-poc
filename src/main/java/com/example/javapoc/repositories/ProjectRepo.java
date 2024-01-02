package com.example.javapoc.repositories;

import com.example.javapoc.entities.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends ReactiveMongoRepository<Project,String> {


}

