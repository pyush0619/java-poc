package com.example.javapoc.service;

import com.example.javapoc.entities.Elements;
import com.example.javapoc.exception.ElementNotFoundException;
import com.example.javapoc.repositories.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class ElementService {

    @Autowired
    private ElementRepository elementRepository;

    /**
     * Get Comments for give elementId
     *
     * @param projectId, slateId, elementId
     * @return
     */

    public Mono<Elements> getElementBySlateManifestUrnAndWorkUrn(@PathVariable("projectId") String projectId, @PathVariable("slateId") String slateId, @PathVariable("elementId") String elementId) {
        return elementRepository.findBySlateManifestUrnAndWorkUrn(slateId, elementId)
                .switchIfEmpty(Mono.error(new ElementNotFoundException("There is no Element with id: " + elementId)));
    }
    /**
     * Get Comments for give elementId
     *
     * @param projectId, slateId
     * @return
     */
    public Flux<Elements> getAllElements(@PathVariable("projectId") String projectId,@PathVariable("slateId")String slateId) {

        return elementRepository.findBySlateManifestUrn(slateId)
                .switchIfEmpty(Flux.error(new ElementNotFoundException("There is no Element with id: " + slateId)));
    }


    public Mono<Elements> addElement(Elements elements) {
        String entityUrnToAdd="urn:entity:"+ UUID.randomUUID().toString();
        String workUrnToAdd="urn:work:"+UUID.randomUUID().toString();
        elements.setEntityUrn(entityUrnToAdd);
        elements.setWorkUrn(workUrnToAdd);
        elements.setCreatedTimestamp(Instant.now());
        elements.setUpdatedTimestamp(Instant.now());
        return elementRepository.save(elements);
    }

    /**
     * Get Comments for give elementId
     *
     * @param projectId, slateId, elementId
     * @return
     */
    public Mono<Void> deleteElement(@PathVariable("projectId") String projectId,@PathVariable("slateId") String slateId, @PathVariable("elementId") String elementId) {

        return elementRepository.deleteById(elementId);
    }

    public Mono<Elements> updateElement(@PathVariable("projectId") String projectId, @PathVariable("slateId") String slateId, @PathVariable("elementId") String elementId, Elements elements) {
        return elementRepository.findBySlateManifestUrnAndWorkUrn(slateId, elementId)
                .flatMap(s -> {
                    s.setType(elements.getType());
                    s.setText(elements.getText());
                    s.setUpdatedBy(elements.getUpdatedBy());
                    s.setUpdatedTimestamp(Instant.now());
                    return elementRepository.save(s);
                });
    }
}



