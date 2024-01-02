package com.example.javapoc.handler;

import com.example.javapoc.entities.Slate;
import com.example.javapoc.service.SlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SlateHandler {
    @Autowired
    SlateService slateService;
    /**
     * handler function for getting all slates icons for an element
     *
     * @param serverRequest
     * @return
     */


    public Mono<ServerResponse> getSlateByDistributableUrn(ServerRequest serverRequest){
        String distributableUrn = serverRequest.pathVariable("projectId");
        Flux<Slate> slateBydistributableUrn = slateService.getSlateByDistributableUrn(distributableUrn);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(slateBydistributableUrn, Slate.class);
    }


    public Mono<ServerResponse> getSlateByManifestUrnAndDistributableUrn(ServerRequest serverRequest){
        String manifestUrn=serverRequest.pathVariable("slateId");
        String distributableUrn=serverRequest.pathVariable("projectId");
        Mono<Slate> slateByProjectIdAndSlateId= slateService.getSlateByManifestUrnAndDistributableUrn(manifestUrn,distributableUrn);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(slateByProjectIdAndSlateId, Slate.class);

    }

    public Mono<ServerResponse> addSlateByManifestUrnAndDistributableUrn(ServerRequest serverRequest) {
        Mono<Slate> slateTOAdd = serverRequest.bodyToMono(Slate.class);
        return slateTOAdd.flatMap(result ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(slateService.addSlateByManifestUrnAndDistributableUrn(result),Slate.class));
    }


    public Mono<ServerResponse> deleteSlateByManifestUrnAndDistributableUrn(ServerRequest serverRequest) {
        String manifestUrn=serverRequest.pathVariable("slateId");
        String distibutableUrn=serverRequest.pathVariable("projectId");
        Mono<Void> slateDeleted = slateService.deleteSlateByManifestUrnAndDistributableUrn(manifestUrn,distibutableUrn);
        return ServerResponse.ok().body(slateDeleted,Slate.class);
    }



    public Mono<ServerResponse> updateSlateByManifestUrnAndDistributableUrn(ServerRequest serverRequest) {
        String manifestUrn=serverRequest.pathVariable("slateId");
        String distributableUrn=serverRequest.pathVariable("projectId");
        Mono<Slate>slateMono=serverRequest.bodyToMono(Slate.class);
        return slateMono.flatMap(s->
                ServerResponse.status(HttpStatus.OK)
                        .contentType((MediaType.APPLICATION_JSON))
                        .body(slateService.updateSlateByManifestUrnAndDistributableUrn(manifestUrn,distributableUrn,s),Slate.class));

    }
}
