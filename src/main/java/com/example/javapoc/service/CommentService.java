package com.example.javapoc.service;
import com.example.javapoc.entities.Comment;
import com.example.javapoc.entities.Reply;
import com.example.javapoc.exception.CommentNotFound;
import com.example.javapoc.dto.CommentRequest;
import com.example.javapoc.dto.ReplyRequest;
import com.example.javapoc.repositories.CommentRepository;
import com.example.javapoc.repositories.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;
@Service
@Slf4j
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;

    /**
     * Get Comments for give elementId
     *
     * @param elementId
     * @return
     */
    public Flux<Comment> getCommentsByID(@PathVariable("elementId") String elementId){
        return commentRepository.findByElementWorkUrn(elementId).switchIfEmpty(Mono.error(new CommentNotFound("There is no comment with id: "+elementId)));

    }
    public Mono<Comment> addComment(CommentRequest commentToAdd){
        Comment comment=new Comment();
        String constant="urn:work:";
        String workUrn=constant+UUID.randomUUID().toString();
        comment.setWorkUrn(workUrn);
        comment.setCreatedTimestamp(Instant.now());
        comment.setCreatedBy(commentToAdd.getUserId());
        comment.setSlateManifestUrn(commentToAdd.getSlateManifestUrn());
        comment.setType(commentToAdd.getType());
        comment.setText(commentToAdd.getText());
        comment.setElementWorkUrn(commentToAdd.getElementWorkUrn());
        comment.setEntityUrn(commentToAdd.getEntityUrn());
        return commentRepository.save(comment);
    }
    /**
     * delete comment for the given commentId
     *
     * @param commentId,elementId
     * @return
     */
    public Mono<Void> deleteComment(@PathVariable("elementId") String elementId,@PathVariable("commentId") String commentId){
        return commentRepository.deleteByElementWorkUrnAndWorkUrn(elementId,commentId);
    }
    /**
     * Update Comment for give commentId
     *
     * @param commentId,elementId
     * @return
     */
    public  Mono<Comment> updateComment(@PathVariable("elementId") String elementId,@PathVariable("commentId") String commentId,CommentRequest comment){
        log.info("update comment");
        return commentRepository.findByElementWorkUrnAndWorkUrn(elementId,commentId).flatMap(
                s->{s.setType(comment.getType());
                    log.info("existing object {}",s);
                s.setText(comment.getText());
                s.setUpdatedBy(comment.getUserId());
                s.setEntityUrn(comment.getEntityUrn());
                s.setUpdatedTimestamp(Instant.now());
                return commentRepository.save(s);});
    }
    /**
     * Get Replies for give commentId
     *
     * @param commentId
     * @return
     */
    public Flux<Reply> getRepliesById(@PathVariable("commentId") String commentId){
        return replyRepository.findByCommentUrn(commentId).switchIfEmpty(Mono.error(new CommentNotFound("There is no reply with id: "+commentId)));
    }
    public Mono<Reply> addReply(ReplyRequest replyToAdd){
        Reply reply=new Reply();
        String constant="urn:work:";
        String workUrnToAdd=constant+UUID.randomUUID().toString();
        reply.setWorkUrn(workUrnToAdd);
        reply.setCommentUrn(replyToAdd.getCommentUrn());
        reply.setEntityUrn(replyToAdd.getEntityUrn());
        reply.setCreatedBy(replyToAdd.getUserId());
        reply.setCreatedTimestamp(Instant.now());
        reply.setText(replyToAdd.getText());
        reply.setType(replyToAdd.getType());
        return replyRepository.save(reply);
    }
    /**
     * delete reply with given replyId
     *
     * @param replyId,commentId
     * @return
     */
    public Mono<Void> deleteReply(@PathVariable("commentId") String commentId ,@PathVariable("replyId") String replyId){
        return replyRepository.deleteByCommentUrnAndWorkUrn(commentId,replyId);
    }
    /**
     * update the reply with given replyId
     *
     * @param replyId,commentId
     * @return
     */
    public  Mono<Reply> updateReply(@PathVariable("commentId") String commentId, @PathVariable("replyId") String replyId, ReplyRequest reply){
        return replyRepository.findByCommentUrnAndWorkUrn(commentId,replyId).flatMap(
                s->{s.setText(reply.getText());
                    s.setUpdatedBy(reply.getUserId());
                    s.setUpdatedTimestamp(Instant.now());
                    return replyRepository.save(s);});
    }
}
