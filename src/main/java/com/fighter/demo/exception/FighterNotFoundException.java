package com.fighter.demo.exception;

public class FighterNotFoundException extends RuntimeException {

    public FighterNotFoundException(String message) {
        super(message);
    }
}
