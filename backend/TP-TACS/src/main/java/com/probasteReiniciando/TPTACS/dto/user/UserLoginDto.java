package com.probasteReiniciando.TPTACS.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
public class UserLoginDto {
    @NotEmpty
    private final String username;
    @NotEmpty
    private final String password;
}
