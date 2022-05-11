package com.probasteReiniciando.TPTACS.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String param) {

        super(String.format("User   with username : '%s' already exists", param));
    }



}