package com.probasteReiniciando.TPTACS.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterRequestDto {

    //TODO meter validaciones de regex por ejemplo, long minima, etc
    private String password;
    private String email;
    private String username;

}