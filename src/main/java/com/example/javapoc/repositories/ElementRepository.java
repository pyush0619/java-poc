package com.example.javapoc.repositories;

import com.example.javapoc.entities.Elements;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ElementRepository extends ReactiveMongoRepository<Elements, String> {
    Flux<Elements> findBySlateManifestUrn(String slateManifestUrn);
    Mono<Elements> findBySlateManifestUrnAndWorkUrn( String slateManifestUrn, String workUrn);
    Mono<Void> deleteBySlateManifestUrnAndWorkUrn( String slateManifestUrn, String workUrn);
}
