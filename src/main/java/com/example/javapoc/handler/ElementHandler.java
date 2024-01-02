package com.example.javapoc.handler;
import com.example.javapoc.entities.Elements;
import com.example.javapoc.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class ElementHandler {

    @Autowired
    ElementService elementService;
    /**
     * handler function for getting all comments icons for an element
     *
     * @param serverRequest
     * @return
     */

    public Mono<ServerResponse> getAllElements(ServerRequest serverRequest) {
        String distributableUrn = serverRequest.pathVariable("projectId");
        String slateManifestUrn=serverRequest.pathVariable("slateId");
        Flux<Elements> AllElements =elementService.getAllElements(distributableUrn,slateManifestUrn);
        return ServerResponse.ok().body(AllElements,Elements.class);
    }

    public Mono<ServerResponse> getElementBySlateManifestUrnAndWorkUrn(ServerRequest serverRequest) {
        String distributableUrn = serverRequest.pathVariable("projectId");
        String slateManifestUrn = serverRequest.pathVariable("slateId");
        String workUrn=serverRequest.pathVariable("elementId");
        Mono<Elements> elementsMono=elementService.getElementBySlateManifestUrnAndWorkUrn(distributableUrn,slateManifestUrn, workUrn);
        return ServerResponse.ok().body(elementsMono,Elements.class);
    }

    public Mono<ServerResponse> addElement(ServerRequest serverRequest){
        Mono<Elements> newElement=serverRequest.bodyToMono(Elements.class);
        return newElement.flatMap(s ->
                ServerResponse.status(OK)
                        .body(elementService.addElement(s), Elements.class));
    }

    public Mono<ServerResponse> deleteElement(ServerRequest serverRequest){
        String distributableUrn = serverRequest.pathVariable("projectId");
        String slateManifestUrn = serverRequest.pathVariable("slateId");
        String workUrn=serverRequest.pathVariable("elementId");
        Mono<Void> elementsMono=elementService.deleteElement(distributableUrn,slateManifestUrn, workUrn);
        return ServerResponse.ok().body(elementsMono,Elements.class);
    }

    public Mono<ServerResponse> updateElement(ServerRequest serverRequest) {
        String distributableUrn = serverRequest.pathVariable("projectId");
        String slateManifestUrn = serverRequest.pathVariable("slateId");
        String workUrn=serverRequest.pathVariable("elementId");
        Mono<Elements> elementsMono = serverRequest.bodyToMono(Elements.class);
        return elementsMono.flatMap(s ->
                ServerResponse.status(OK)
                        .contentType(APPLICATION_JSON)
                        .body(elementService.updateElement(distributableUrn,slateManifestUrn, workUrn, s), Elements.class));
    }


}
