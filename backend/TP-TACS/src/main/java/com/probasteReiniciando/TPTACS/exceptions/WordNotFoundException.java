package com.probasteReiniciando.TPTACS.exceptions;

public class WordNotFoundException extends RuntimeException {

    public WordNotFoundException(String param) {

        super(String.format("The  word : '%s' has not been found", param));
    }



}