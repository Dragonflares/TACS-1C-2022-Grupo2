package com.probasteReiniciando.TPTACS.exceptions;

public class TournamentNotFoundException extends RuntimeException {

    public TournamentNotFoundException(String param) {

        super(String.format("Tournament not found with id : '%s'", param));
    }


}