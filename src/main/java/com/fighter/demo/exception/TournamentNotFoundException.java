package com.fighter.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class returns HttpStatus.NOT_FOUND when a non existing tournament id is provided
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Ingen sån turnering hittades")
public class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException( ) {
    }
}
