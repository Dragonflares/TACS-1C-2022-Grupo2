package com.probasteReiniciando.TPTACS.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String param) {

        super(String.format("User  not found with username : '%s'", param));
    }



    }
