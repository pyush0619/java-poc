package com.example.javapoc.service;

import com.example.javapoc.entities.Elements;
import com.example.javapoc.repositories.ElementRepository;
import com.example.javapoc.service.ElementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ElementServiceTest {
    @InjectMocks
    private ElementService elementService;
    @Mock
    private ElementRepository elementRepository;

    String workUrn="urn:work:"+UUID.randomUUID().toString();
    String entityUrn="urn:entity"+UUID.randomUUID().toString();
    Instant createdTimestamp = Instant.now();
    Instant updatedTimestamp = Instant.now();


    @Test
    void getAllElements() {
        Elements elements = new Elements("workUrn", "entityUrn","apq", "6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", "hello", "tanvi", createdTimestamp, "tanvi", updatedTimestamp );
        Mockito.when(elementRepository.findBySlateManifestUrn("6a9abf7f-aa9d-4774-9a02-b168ce5a46g9")).thenReturn(Flux.just(elements));
        Flux<Elements> resultFlux = elementService.getAllElements("projectId",elements.getSlateManifestUrn());
        StepVerifier.create(resultFlux).expectSubscription()
                .expectNext(new Elements("workUrn", "entityUrn" , "apq", "6a9abf7f-aa9d-4774-9a02-b168ce5a46g9" , "hello" , "tanvi", createdTimestamp, "tanvi", updatedTimestamp))
                .verifyComplete();
    }

    @Test
    void addElement() {
        Elements elements = new Elements("workUrn", "entityUrn", "apq", "6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", "hello", "tanvi", createdTimestamp, "tanvi", updatedTimestamp);
        Elements elementDto = new Elements("workUrn", "entityUrn", "apq", "6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", "hello", "tanvi", createdTimestamp, "tanvi", updatedTimestamp);
        Mockito.when(elementRepository.save(any(Elements.class))).thenReturn(Mono.just(elements));
        Mono<Elements> elementsMono = elementService.addElement(elementDto);
        elementsMono.subscribe(res -> {
            assertTrue(res.getText().contains("hello"));
        });
    }
    @Test
    void deleteElement(){
        Elements elements = new Elements("workUrn", "entityUrn", "apq", "6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", "hello", "tanvi", createdTimestamp, "tanvi", updatedTimestamp);
        Mockito.when(elementRepository.deleteById(workUrn)).thenReturn(Mono.empty());
        Mono<Void> elementMono = elementService.deleteElement("projectId",elements.getSlateManifestUrn(), workUrn);
        StepVerifier.create(elementMono).expectComplete().verify();

    }

    @Test
    void getElementBySlateManifestUrnAndWorkUrn() {

        Elements elements = new Elements("workUrn", "entityUrn", "apq", "6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", "hello", "tanvi", createdTimestamp, "tanvi", updatedTimestamp);
        Mockito.when(elementRepository.findBySlateManifestUrnAndWorkUrn("6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", workUrn)).thenReturn(Mono.just(elements));
        Mono<Elements> elementsMono = elementService.getElementBySlateManifestUrnAndWorkUrn("projectId",elements.getSlateManifestUrn(), workUrn);
        StepVerifier.create(elementsMono).consumeNextWith(newElement -> {
            assertEquals("workUrn", newElement.getWorkUrn());
        }).verifyComplete();
    }

    @Test
    void updateElement(){
        Elements elements = new Elements("workUrn", "entityUrn", "apq", "6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", "hello", "tanvi", createdTimestamp, "tanvi", updatedTimestamp);
        Mockito.when(elementRepository.findById("workUrn")).thenReturn(Mono.just(elements));
        Mockito.when(elementRepository.save(ArgumentMatchers.any(Elements.class))).thenReturn(Mono.just(elements));
        Mono<Elements> resultMono = elementService.updateElement("projectId","6a9abf7f-aa9d-4774-9a02-b168ce5a46g9", workUrn, elements);
        StepVerifier.create(resultMono).expectNextMatches(res-> res.getText().contains("hello"))
                .verifyComplete();
    }
}
