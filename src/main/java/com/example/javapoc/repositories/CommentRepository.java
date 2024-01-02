package com.example.javapoc.repositories;

import com.example.javapoc.entities.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment,String> {
    Flux<Comment> findByElementWorkUrn(String elementWorkUrn);
    Mono<Comment> findByElementWorkUrnAndWorkUrn(String elementWorkUrn,String workUrn);
    Mono<Void> deleteByElementWorkUrnAndWorkUrn(String elementWorkUrn,String workUrn);

}
