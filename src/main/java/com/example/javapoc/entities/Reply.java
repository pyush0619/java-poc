package com.example.javapoc.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "reply")
public class Reply {
    @Id
    String workUrn;
    String entityUrn;
    Instant createdTimestamp;
    Instant updatedTimestamp;
    String createdBy;
    String updatedBy;
    String commentUrn;
    String type;
    String text;
}
