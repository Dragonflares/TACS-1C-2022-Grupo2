package com.probasteReiniciando.TPTACS.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
@Builder
public class UserDto {
    @NonNull
    private String name;
}
