package com.probasteReiniciando.TPTACS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class UserDto {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
