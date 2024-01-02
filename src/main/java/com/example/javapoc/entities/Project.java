package com.example.javapoc.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "project")

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    private String distributableUrn;
    private String entityUrn;
    private String title;
    private String author;
    private String createdBy;
    private Instant createdTimestamp;
    private String updatedBy;
    private Instant updatedTimestamp;

}

