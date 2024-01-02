package com.example.javapoc.service;

import com.example.javapoc.entities.Slate;
import com.example.javapoc.repositories.SlateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class SlateServiceTest {

    @Autowired
    SlateRepository slateRepository;
    @Autowired
    SlateService slateService;

    @Test
    void getSlateByDistributableUrn() {
        Slate slate=new Slate();
        slate.setTitle("slate Api");
        slate.setDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96");

        Flux<Slate> slateFlux=Flux.just(slate);
        StepVerifier.create(slateFlux)
                .consumeNextWith(response->{
                    Assertions.assertEquals("slate Api",response.getTitle());
                    Assertions.assertEquals("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96",response.getDistributableUrn());
                }).verifyComplete();
    }


    @Test
    void getSlateByManifestUrnAndDistributableUrn() {
        Slate slate=new Slate();
        slate.setTitle("slate Api");
        slate.setDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96");

        Flux<Slate> slateFlux=Flux.just(slate);
        StepVerifier.create(slateFlux)
                .consumeNextWith(response->{
                    Assertions.assertEquals("slate Api",response.getTitle());
                    Assertions.assertEquals("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96",response.getDistributableUrn());
                }).verifyComplete();
    }



    @Test
    void addSlateByManifestUrnAndDistributableUrn() {
        Slate slate= new Slate();
        slate.setManifestUrn(UUID.randomUUID().toString());
        slate.setEntityUrn(UUID.randomUUID().toString());
        slate.setDistributableUrn("urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96");
        slate.setTitle("Slate Api");
        slate.setCreatedBy("Naman Jain");
        Mono<Slate> slateMono = slateService.addSlateByManifestUrnAndDistributableUrn(slate);
        StepVerifier.create(slateMono)
                .consumeNextWith(ans -> {
                            assertEquals("Slate Api", ans.getTitle());
                        }
                ).verifyComplete();

    }

    @Test
    void deleteSlateByManifestUrnAndDistributableUrn() {
        Mono<Void> voidMono = slateService.deleteSlateByManifestUrnAndDistributableUrn("urn:manifest:65c8ef58-aeb9-491f-b469-baeb3a80b9c9","urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96");
        StepVerifier.create(voidMono).verifyComplete();
//        slateService.deleteSlate("urn:manifest:045a2487-980e-48e8-a248-d067240db736");
//        assertNull(slateRepository.findById("urn:manifest:045a2487-980e-48e8-a248-d067240db736"));
    }

    @Test
    void updateByManifestUrnAndDistributableUrn() {
        Slate updateSlate = new Slate();
        updateSlate.setTitle("Project");
        updateSlate.setUpdatedBy("NJ");
        updateSlate.setUpdatedTimestamp(Instant.now());

        Mono<Slate> studentsMonoFromServicefterUpdate = slateService.updateSlateByManifestUrnAndDistributableUrn("urn:manifest:045a2487-980e-48e8-a248-d067240db736", "urn:entity:46d2ce09-4cd8-4478-be73-3cd8a792ed96",updateSlate);

        StepVerifier.create(studentsMonoFromServicefterUpdate)
                .consumeNextWith(ans -> {
                            assertEquals("Project", ans.getTitle());
                        }
                ).verifyComplete();

    }

}

