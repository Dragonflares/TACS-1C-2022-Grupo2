package com.probasteReiniciando.TPTACS.exceptions;

public class ResultBadRequestException extends RuntimeException {

    public ResultBadRequestException() {

        super("Result with bad data");

    }
}
