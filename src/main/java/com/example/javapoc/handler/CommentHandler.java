package com.example.javapoc.handler;

import com.example.javapoc.entities.Comment;
import com.example.javapoc.entities.Reply;
import com.example.javapoc.dto.CommentRequest;
import com.example.javapoc.dto.ReplyRequest;
import com.example.javapoc.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CommentHandler {
    @Autowired
    CommentService commentService;

    /**
     * handler function for getting all comments icons for an element
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getCommentsById(ServerRequest serverRequest){
        String Id = serverRequest.pathVariable("elementId");
        Flux<Comment> commentsById = commentService.getCommentsByID(Id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(commentsById, Comment.class);
    }
    /**
     * handler function for adding a comment
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> addComment(ServerRequest serverRequest){
        Mono<CommentRequest> commentToAdd = serverRequest.bodyToMono(CommentRequest.class);
        return commentToAdd.flatMap(result ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(commentService.addComment(result),Comment.class));
    }
    /**
     * handler function for deleting a comment
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> deleteComment(ServerRequest serverRequest){
        String commentId = serverRequest.pathVariable("commentId");
        String elementId = serverRequest.pathVariable("elementId");
        Mono<Void> commentDeleted = commentService.deleteComment(elementId,commentId);
        return ServerResponse.ok().body(commentDeleted,Comment.class);
    }
    /**
     * handler function for updating a comment
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> updateComment(ServerRequest serverRequest){
        String commentId =serverRequest.pathVariable("commentId");
        String elementId = serverRequest.pathVariable("elementId");
        Mono<CommentRequest> commentMono=serverRequest.bodyToMono(CommentRequest.class);
        log.info("update comments handler");
        return commentMono.flatMap(s->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(commentService.updateComment(elementId,commentId,s),Comment.class));
    }
    /**
     * handler function for getting all replies for a comment
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getRepliesById(ServerRequest serverRequest){
        String Id = serverRequest.pathVariable("commentId");
        Flux<Reply> repliesById = commentService.getRepliesById(Id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repliesById, Reply.class);
    }
    /**
     * handler function for adding a reply
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> addReply(ServerRequest serverRequest){
        Mono<ReplyRequest> replyToAdd = serverRequest.bodyToMono(ReplyRequest.class);
        return replyToAdd.flatMap(result ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(commentService.addReply(result),Reply.class));
    }
    /**
     * handler function for deleting a reply
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> deleteReply(ServerRequest serverRequest){
        String replyId = serverRequest.pathVariable("replyId");
        String commentId=serverRequest.pathVariable("commentId");
        Mono<Void> replyDeleted = commentService.deleteReply(commentId,replyId);
        return ServerResponse.ok().body(replyDeleted,Reply.class);
    }
    /**
     * handler function for updating a reply
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> updateReply(ServerRequest serverRequest){
        String replyId =serverRequest.pathVariable("replyId");
        String commentId=serverRequest.pathVariable("commentId");

        Mono<ReplyRequest> replyMono=serverRequest.bodyToMono(ReplyRequest.class);
        return replyMono.flatMap(s->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(commentService.updateReply(commentId,replyId,s),Reply.class));
    }
}
