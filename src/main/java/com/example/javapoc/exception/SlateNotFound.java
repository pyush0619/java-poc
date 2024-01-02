package com.example.javapoc.exception;

public class SlateNotFound extends RuntimeException{
    private  String message;
    public SlateNotFound() {
        super();
    }

    public SlateNotFound(String message) {
        super(message);
        this.message = message;
    }
}




