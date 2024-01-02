package com.example.javapoc.handler;
import com.example.javapoc.handler.ElementHandler;
import com.example.javapoc.entities.Elements;
import com.example.javapoc.service.ElementService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@SpringBootTest
class ElementHandlerTest {
    @InjectMocks
    private ElementHandler elementHandler;
    @Mock
    private ElementService elementService;

    @Mock
    private ServerRequestWrapper serverRequestWrapper;

    @BeforeAll
    void setup(){

        ReflectionTestUtils.setField(elementHandler, "elementService", elementService);
    }
    @Test
    void getAllElements(){
        when(serverRequestWrapper.pathVariable("projectId")).thenReturn("projectId");
        when(serverRequestWrapper.pathVariable("slateId")).thenReturn("slateId");
        when(elementService.getAllElements(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(Flux.just(new Elements()));
        Mono<ServerResponse> serverResponse = elementHandler.getAllElements(serverRequestWrapper);
        serverResponse.subscribe(resp ->{
            assertEquals(HttpStatus.OK , resp.statusCode());
        });
    }

    @Test
    void getElementBySlateManifestUrnAndWorkUrn(){
        when(serverRequestWrapper.pathVariable("projectId")).thenReturn("projectId");
        when(serverRequestWrapper.pathVariable("slateId")).thenReturn("slateId");
        when(serverRequestWrapper.pathVariable("elementId")).thenReturn("elementTd");
        when(elementService.getElementBySlateManifestUrnAndWorkUrn(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(Mono.just(new Elements()));
        Mono<ServerResponse> serverResponse = elementHandler.getElementBySlateManifestUrnAndWorkUrn(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK , resp.statusCode());
        });

    }

    @Test
    void addElement(){
        when(serverRequestWrapper.bodyToMono(Elements.class)).thenReturn(Mono.just(new Elements()));
        when(elementService.addElement(ArgumentMatchers.any())).thenReturn(Mono.just(new Elements()));
        Mono<ServerResponse> serverResponse = elementHandler.addElement(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });
    }
    @Test
    void deleteElement(){
        when(serverRequestWrapper.pathVariable("projectId")).thenReturn("projectId");
        when(serverRequestWrapper.pathVariable("slateId")).thenReturn("slateId");
        when(serverRequestWrapper.pathVariable("elementId")).thenReturn("elementId");
        when(elementService.deleteElement(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(Mono.empty());
        Mono<ServerResponse> serverResponse = elementHandler.deleteElement(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });
    }

    @Test
    void updateElement(){
        when(serverRequestWrapper.pathVariable("projectId")).thenReturn("projectId");
        when(serverRequestWrapper.pathVariable("slateId")).thenReturn("slateId");
        when(serverRequestWrapper.pathVariable("elementId")).thenReturn("elementId");
        when(serverRequestWrapper.bodyToMono(Elements.class)).thenReturn(Mono.just(new Elements()));
        when(elementService.updateElement(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(Mono.just(new Elements()));
        Mono<ServerResponse> serverResponse = elementHandler.updateElement(serverRequestWrapper);
        serverResponse.subscribe(resp -> {
            assertEquals(HttpStatus.OK, resp.statusCode());
        });
    }

}
