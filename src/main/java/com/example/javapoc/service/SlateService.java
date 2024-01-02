package com.example.javapoc.service;

import com.example.javapoc.entities.Slate;
import com.example.javapoc.exception.SlateNotFound;
import com.example.javapoc.repositories.SlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;

@Service
public class SlateService {
    @Autowired
    SlateRepository slateRepository;


    //get all slates of a particular project
    /**
     * Get all Slates for a particular project by distributableUrn(projectId)
     *
     * @param projectId
     * @return
     */
    public Flux<Slate> getSlateByDistributableUrn(@PathVariable("projectId") String projectId) {

        return slateRepository.findBydistributableUrn(projectId).switchIfEmpty(Mono.error(new SlateNotFound("Slate not found with this URN"+projectId)));
    }

    //get a particular slate by their Id
    /**
     * Get Slate for given manifestUrn(slateId) and distributableUrn(projectId)
     *
     * @param slateId
     * @return
     */
    public Mono<Slate> getSlateByManifestUrnAndDistributableUrn(@PathVariable("slateId") String slateId,@PathVariable("projectId") String projectId){

        return slateRepository.findByManifestUrnAndDistributableUrn(slateId,projectId).switchIfEmpty(Mono.error(new SlateNotFound("Slate not found with this URN"+slateId)));

    }

    //Adding a Slate
    public Mono<Slate> addSlateByManifestUrnAndDistributableUrn(Slate slate){
        String manifestUrnToadd= "urn:manifest:" + UUID.randomUUID();
        String entityUrnToadd= "urn:entity:"+UUID.randomUUID();
        slate.setCreatedTimestamp(Instant.now());
        slate.setManifestUrn(manifestUrnToadd);
        slate.setEntityUrn(entityUrnToadd);
        return slateRepository.save(slate);
    }


    /**
     * delete slate for given manifestUrn(slateId) and distributableUrn(projectId)
     *
     * @param slateId,projectId
     * @return
     */
    public Mono<Void> deleteSlateByManifestUrnAndDistributableUrn(@PathVariable("slateId") String slateId,@PathVariable("projectId") String projectId){
               return slateRepository.deleteByManifestUrnAndDistributableUrn(slateId,projectId);

    }

    /**
     * update slate for given slateId
     *
     * @param slateId,projectId
     * @return
     */
    public Mono<Slate> updateSlateByManifestUrnAndDistributableUrn(@PathVariable("slateId") String slateId,@PathVariable("projectId") String projectId, Slate slate){


        return slateRepository.findByManifestUrnAndDistributableUrn(slateId,projectId).flatMap(
                s-> {
                    s.setTitle(slate.getTitle());
                    s.setCreatedBy(slate.getCreatedBy());
                    s.setUpdatedBy(slate.getUpdatedBy());
                    s.setUpdatedTimestamp(Instant.now());
                    return slateRepository.save(s);
                });


    }

}
