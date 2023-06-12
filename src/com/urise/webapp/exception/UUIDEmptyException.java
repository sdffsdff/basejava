package com.urise.webapp.exception;

public class UUIDEmptyException extends RuntimeException{
    public UUIDEmptyException() {
        super("ERROR:UUID can't be empty or null.");
    }
}
