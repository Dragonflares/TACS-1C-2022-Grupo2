package com.probasteReiniciando.TPTACS.dto.user;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserDto {
    @NonNull
    private final String name;
}
