package com.fighter.demo.exception;

public class TournamentEndedException extends RuntimeException {
    public TournamentEndedException(String message) {
        super(message);
    }
}
