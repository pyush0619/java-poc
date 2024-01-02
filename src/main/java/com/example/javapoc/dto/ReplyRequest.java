package com.example.javapoc.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {
    String entityUrn;
    String userId;
    String commentUrn;
    String type;
    String text;
}
