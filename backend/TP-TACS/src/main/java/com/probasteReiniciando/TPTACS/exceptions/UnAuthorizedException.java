package com.probasteReiniciando.TPTACS.exceptions;

public class UnAuthorizedException extends RuntimeException{

    public UnAuthorizedException(String param) {

        super(String.format("User has not authorized given", param));
    }

}
