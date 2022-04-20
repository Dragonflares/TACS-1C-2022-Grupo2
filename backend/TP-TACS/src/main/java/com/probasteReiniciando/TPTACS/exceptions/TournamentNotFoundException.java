package com.probasteReiniciando.TPTACS.exceptions;

public class TournamentNotFoundException extends Exception {

    public TournamentNotFoundException(String param) {

        super(String.format("Tournament  not found with id : '%s'", param));
    }


}