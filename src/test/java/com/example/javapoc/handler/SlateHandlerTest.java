package com.example.javapoc.handler;

import com.example.javapoc.entities.Slate;
import com.example.javapoc.service.SlateService;
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

import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@SpringBootTest

class SlateHandlerTest {

    @Autowired
    SlateHandler slateHandler;

    @Autowired
    WebTestClient webTestClient;

    @Mock
    SlateService slateService;

    @BeforeAll
    void setup(){
        ReflectionTestUtils.setField(slateHandler, "slateService", slateService);
    }


    @Test
    void getSlateByDistributableUrn() {
        Slate slate=new Slate();
        slate.setManifestUrn("urn:manifest:9d8aa301-9306-491b-9d1d-3b5d771755e8");
        slate.setEntityUrn("urn:entity:dbaeaab4-f708-4850-a8cb-7f020ed9a88b");
        slate.setTitle("POC");
        slate.setDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96");
        Mockito.when(slateService.getSlateByDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96")).thenReturn(Flux.just(slate));
        webTestClient.get()
                .uri("/projects/urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96/slates")
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    void getSlateByManifestUrnAndDistributableUrn() {
        Slate slate=new Slate();
        slate.setManifestUrn("urn:manifest:9d8aa301-9306-491b-9d1d-3b5d771755e8");
        slate.setEntityUrn("urn:entity:dbaeaab4-f708-4850-a8cb-7f020ed9a88b");
        slate.setTitle("POC");
        slate.setDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96");
        Mockito.when(slateService.getSlateByManifestUrnAndDistributableUrn("urn:manifest:9d8aa301-9306-491b-9d1d-3b5d771755e7","urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96")).thenReturn(Mono.just(slate));
        webTestClient.get()
                .uri("/projects/urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96/slates/urn:manifest:9d8aa301-9306-491b-9d1d-3b5d771755e7")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void addSlate() {
        Slate slate=new Slate();
        slate.setManifestUrn(UUID.randomUUID().toString());
        slate.setEntityUrn(UUID.randomUUID().toString());
        slate.setTitle("POC");
        slate.setDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed97");
        Mockito.when(slateService.addSlateByManifestUrnAndDistributableUrn(slate)).thenReturn(Mono.just(slate));
        webTestClient.post()
                .uri("/projects/urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed97/slates")
                .exchange()
                .expectStatus()
                .isOk();
    }


    @Test
    void deleteSlateByManifestUrnAndDistributableUrn() {
        Mockito.when(slateService.deleteSlateByManifestUrnAndDistributableUrn("urn:manifest:29c7d0c4-6453-4d1f-bc68-548104467995","urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96")).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/projects/urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96/slates/urn:manifest:29c7d0c4-6453-4d1f-bc68-548104467995")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void updateSlateByManifestUrnAndDistributableUrn() {
        Slate slate=new Slate();
        slate.setManifestUrn("urn:manifest:9d8aa301-9306-491b-9d1d-3b5d771755e8");
        slate.setEntityUrn("urn:entity:dbaeaab4-f708-4850-a8cb-7f020ed9a88b");
        slate.setTitle("Slate Api");
        slate.setDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed97");

        Mockito.when(slateService.updateSlateByManifestUrnAndDistributableUrn("urn:manifest:045a2487-980e-48e8-a248-d067240db736","urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96",slate)).thenReturn(Mono.just(slate));
        webTestClient.put()
                .uri("/projects/urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96/slates/urn:manifest:045a2487-980e-48e8-a248-d067240db736")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
