package com.probasteReiniciando.TPTACS.dto.user;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserLoginDto {
    @NotEmpty
    private  String username;
    @NotEmpty
    private  String password;
}
