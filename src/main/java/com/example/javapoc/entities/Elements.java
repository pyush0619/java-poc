package com.example.javapoc.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "elements")
public class Elements {
    @Id
    private String workUrn;
    private String entityUrn;
    private String type;
    private String slateManifestUrn;
    private String text;
    private String createdBy;
    private Instant createdTimestamp;
    private String updatedBy;
    private Instant updatedTimestamp;
}