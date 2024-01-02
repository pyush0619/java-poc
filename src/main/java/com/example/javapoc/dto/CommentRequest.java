package com.example.javapoc.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    String entityUrn;
    String userId;
    String slateManifestUrn;
    String elementWorkUrn;
    String type;
    String text;
}
