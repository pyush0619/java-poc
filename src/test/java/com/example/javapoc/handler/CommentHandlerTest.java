package com.example.javapoc.handler;

import com.example.javapoc.entities.Comment;
import com.example.javapoc.entities.Reply;
import com.example.javapoc.dto.CommentRequest;
import com.example.javapoc.dto.ReplyRequest;
import com.example.javapoc.service.CommentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@SpringBootTest
public class CommentHandlerTest {
    @Autowired
    CommentHandler commentHandler;
    @Autowired
    WebTestClient webTestClient;
    @Mock
    CommentService commentService;
    @BeforeAll
    void setup(){
        ReflectionTestUtils.setField(commentHandler, "commentService", commentService);
    }
    @Test
    void addcComment() {
        CommentRequest comment=new CommentRequest();
        Comment comments=new Comment();
        comment.setElementWorkUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        comment.setText("hello");
        comment.setType("text");
        comment.setUserId("I1077");
        comment.setSlateManifestUrn("urn:manifest:35f66f90-863d-4382-8978-4f4e456f9cat");
        comment.setEntityUrn("urn:entity:35f66f90-863d-4482-8978-4f4e456f9cab");
        Mockito.when(commentService.addComment(comment)).thenReturn(Mono.just(comments));
        webTestClient.post()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")
                .exchange()
                .expectStatus()
                .isOk();
    }
    @Test
    void getCommentsById() {
        Comment comment=new Comment();
        comment.setElementWorkUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        comment.setText("hello");
        comment.setType("text");
        comment.setCreatedBy("I1077");
        comment.setSlateManifestUrn("urn:manifest:35f66f90-863d-4382-8978-4f4e456f9cat");
        Mockito.when(commentService.getCommentsByID("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")).thenReturn(Flux.just(comment));
        webTestClient.get()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")
                .exchange()
                .expectStatus()
                .isOk();
    }
    @Test
    void updateComment() {
        CommentRequest updateComment = new CommentRequest();
        Comment comment=new Comment();
        updateComment.setText("siddhant");
        updateComment.setType("text");
        Mockito.when(commentService.updateComment("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:35f66f90-863d-4382-8978-4f4e456f9cab", updateComment)).thenReturn(Mono.just(comment));
        webTestClient.put()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/comments/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")
                .exchange()
                .expectStatus()
                .isOk();
    }
    @Test
    void deleteComment() {
        Comment comment=new Comment();
        comment.setElementWorkUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        comment.setText("hello");
        comment.setType("text");
        comment.setCreatedBy("I1077");
        Mockito.when(commentService.deleteComment("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/comments/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")
                .exchange()
                .expectStatus()
                .isOk();
    }
    @Test
    void addReply() {
        ReplyRequest reply=new ReplyRequest();
        Reply replys=new Reply();
        reply.setCommentUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        reply.setText("hello");
        reply.setType("text");
        reply.setUserId("I1077");
        Mockito.when(commentService.addReply(reply)).thenReturn(Mono.just(replys));
        webTestClient.post()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/comments/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")
                .exchange()
                .expectStatus()
                .isOk();
    }
    @Test
    void getRepliesById() {
        Reply reply=new Reply();
        reply.setCommentUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        reply.setText("hello");
        reply.setType("text");
        reply.setCreatedBy("I1077");
        Mockito.when(commentService.getRepliesById("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")).thenReturn(Flux.just(reply));
        webTestClient.get()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/comments/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab")
                .exchange()
                .expectStatus()
                .isOk();
    }


    @Test
    void updateReply() {
        ReplyRequest reply=new ReplyRequest();
        Reply replys=new Reply();
        reply.setCommentUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        reply.setText("hello");
        reply.setType("text");
        Mockito.when(commentService.updateReply("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:83830be6-4c7d-4e87-acd1-e96314ccc17c", reply)).thenReturn(Mono.just(replys));
        webTestClient.put()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/comments/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/replies/urn:work:83830be6-4c7d-4e87-acd1-e96314ccc17c")
                .exchange()
                .expectStatus()
                .isOk();
    }
    @Test
    void deleteReply() {
        ReplyRequest reply=new ReplyRequest();
        reply.setCommentUrn("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab");
        reply.setText("hello");
        reply.setType("text");
        reply.setUserId("I1077");
        Mockito.when(commentService.deleteReply("urn:work:35f66f90-863d-4382-8978-4f4e456f9cab","urn:work:83830be6-4c7d-4e87-acd1-e96314ccc17c")).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/elements/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/comments/urn:work:35f66f90-863d-4382-8978-4f4e456f9cab/replies/urn:work:83830be6-4c7d-4e87-acd1-e96314ccc17c")
                .exchange()
                .expectStatus()
                .isOk();
    }


}

