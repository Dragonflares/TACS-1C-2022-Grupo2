package com.probasteReiniciando.TPTACS.exceptions;

public class TournamentBadRequestException extends Exception {
    public TournamentBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
