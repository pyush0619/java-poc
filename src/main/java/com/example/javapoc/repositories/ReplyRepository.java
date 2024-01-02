package com.example.javapoc.repositories;

import com.example.javapoc.entities.Reply;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReplyRepository extends ReactiveMongoRepository<Reply,String> {
    Flux<Reply> findByCommentUrn(String commentUrn);
    Mono<Reply> findByCommentUrnAndWorkUrn(String commentUrn, String workUrn);
    Mono<Void> deleteByCommentUrnAndWorkUrn(String commentUrn,String workUrn);
}
