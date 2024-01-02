package com.example.javapoc.exception;

public class CommentNotFound extends RuntimeException{
    private String message;

    public CommentNotFound(){
        super();
    }

    public CommentNotFound(String message) {

        super(message);
        this.message = message;
    }
}
