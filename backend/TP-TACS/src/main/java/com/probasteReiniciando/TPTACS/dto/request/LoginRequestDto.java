package com.probasteReiniciando.TPTACS.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
}