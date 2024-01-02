package com.example.javapoc.entities;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "comment")
public class Comment {
    String entityUrn;
    @Id
    String workUrn;
    Instant createdTimestamp;
    Instant updatedTimestamp;
    String createdBy;
    String updatedBy;
    String slateManifestUrn;
    String elementWorkUrn;
    String type;
    String text;

}
