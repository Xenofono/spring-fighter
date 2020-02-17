package com.fighter.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Ingen sån fighter hittades")
public class FighterNotFoundException extends RuntimeException {

    public FighterNotFoundException( ) {

    }
}
