package com.probasteReiniciando.TPTACS.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class UserLoginDto {
    @NonNull
    private final String username;
    @NonNull
    private final String password;
}
