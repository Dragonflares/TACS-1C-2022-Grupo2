package com.probasteReiniciando.TPTACS.exceptions;

import com.probasteReiniciando.TPTACS.domain.Language;

import java.time.LocalDate;

public class ResultAlreadyExistsException extends RuntimeException {

    public ResultAlreadyExistsException(String username, LocalDate date, Language language) {

        super("Result with username " + username + " language " + language.name() + " date " + date + " already exists");

    }

}