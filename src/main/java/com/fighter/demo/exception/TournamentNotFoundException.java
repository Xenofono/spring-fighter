package com.fighter.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Ingen sån turnering hittades")
public class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException( ) {
    }
}
