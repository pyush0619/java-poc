package com.example.javapoc.repositories;

import com.example.javapoc.entities.Slate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SlateRepository extends ReactiveMongoRepository<Slate,String> {
    Flux<Slate> findBydistributableUrn(String distributableUrn);
    Mono<Slate> findByManifestUrnAndDistributableUrn(String manifestUrn,String distributableUrn);
    Mono<Void> deleteByManifestUrnAndDistributableUrn(String manifestUrn,String distributableUrn);
}

