
package com.example.javapoc.service;
import com.example.javapoc.entities.Comment;
import com.example.javapoc.entities.Reply;
import com.example.javapoc.dto.CommentRequest;
import com.example.javapoc.dto.ReplyRequest;
import com.example.javapoc.repositories.CommentRepository;
import com.example.javapoc.repositories.ReplyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    CommentService commentService;

    @Test
    void addComment() {
        CommentRequest comment=new CommentRequest();
        comment.setElementWorkUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        comment.setEntityUrn("urn:entity:35f66f90-863d-4482-8978-4f4e456f9cab");
        comment.setText("hello");
        comment.setType("text");
        comment.setUserId("I1077");
        comment.setSlateManifestUrn("urn:manifest:35f66f90-863d-4382-8978-4f4e456f9cat");
        Mono<Comment> commentMono = commentService.addComment(comment);
        StepVerifier.create(commentMono)
                .consumeNextWith(ans -> {
                            Assertions.assertEquals("hello", ans.getText());
                        }
                ).verifyComplete();

    }

    @Test
    void getCommentById() {
        Flux<Comment> commentFlux = commentService.getCommentsByID("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        StepVerifier.create(commentFlux)
                .consumeNextWith(ans -> {
                            Assertions.assertEquals("hello", ans.getText());
                        }
                ).verifyComplete();
    }

    @Test
    void updateComment() {
        CommentRequest updateComment = new CommentRequest();
        updateComment.setText("siddhant");
        updateComment.setType("text");
        updateComment.setUserId("I-1078");
        Mono<Comment> commentMono = commentService.updateComment("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:35f66f90-863d-4382-8978-4f4e456f9cab", updateComment);
        StepVerifier.create(commentMono)
                .consumeNextWith(ans -> {
                            Assertions.assertEquals("siddhant", ans.getText());
                        }
                ).verifyComplete();
    }
    @Test
    void deleteComment() {
        Mono<Void> studentsMono = commentService.deleteComment("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        StepVerifier.create(studentsMono).verifyComplete();
    }
    @Test
    void addReply() {
        ReplyRequest reply=new ReplyRequest();
        reply.setCommentUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        reply.setText("hello");
        reply.setType("text");
        reply.setUserId("I1077");
        reply.setEntityUrn("urn:work:35f66f90-863d-4382-8978-454e456f9car");
        Mono<Reply> replyMono = commentService.addReply(reply);
        StepVerifier.create(replyMono)
                .consumeNextWith(ans -> {
                            Assertions.assertEquals("hello", ans.getText());
                        }
                ).verifyComplete();

    }

    @Test
    void getRepliesById() {
        Flux<Reply> replyFlux = commentService.getRepliesById("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        StepVerifier.create(replyFlux)
                .consumeNextWith(ans -> {
                            Assertions.assertEquals("hello", ans.getText());
                        }
                ).verifyComplete();
    }

    @Test
    void updateReply() {
        ReplyRequest updateReply = new ReplyRequest();
        updateReply.setText("siddhant");
        updateReply.setType("text");
        updateReply.setUserId("I-1078");
        Mono<Reply> replyMono = commentService.updateReply("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:83830be6-4c7d-4e87-acd1-e96314ccc17c",updateReply);
        StepVerifier.create(replyMono)
                .consumeNextWith(ans -> {
                            Assertions.assertEquals("siddhant", ans.getText());
                        }
                ).verifyComplete();
    }

    @Test
    void deleteStudent() {
        Mono<Void> deleteReply = commentService.deleteReply("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:83830be6-4c7d-4e87-acd1-e96314ccc17c");
        StepVerifier.create(deleteReply).verifyComplete();
    }

}

