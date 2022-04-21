package com.probasteReiniciando.TPTACS.dto.user;

import lombok.*;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserDto {
    @NonNull
    private  String name;
}
