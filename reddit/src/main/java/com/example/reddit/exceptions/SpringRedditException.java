package com.example.reddit.exceptions;

public class SpringRedditException extends RuntimeException{
    public SpringRedditException(String exMessage){
        super(exMessage);
    }
}
