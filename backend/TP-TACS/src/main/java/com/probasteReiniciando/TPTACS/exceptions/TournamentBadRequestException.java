package com.probasteReiniciando.TPTACS.exceptions;

public class TournamentBadRequestException extends RuntimeException {
    public TournamentBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
