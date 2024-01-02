package com.example.javapoc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Document(collection = "slate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Slate {
    @Id
    private String manifestUrn;


    private String entityUrn;
    private String title;
    private String distributableUrn;
    private String createdBy;
    private Instant createdTimestamp;
    private String updatedBy;
    private Instant updatedTimestamp;
}
