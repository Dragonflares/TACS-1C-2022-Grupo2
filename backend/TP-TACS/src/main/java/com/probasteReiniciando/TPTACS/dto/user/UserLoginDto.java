package com.probasteReiniciando.TPTACS.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserLoginDto {
    @NotEmpty
    private  String username;
    @NotEmpty
    private  String password;
}
