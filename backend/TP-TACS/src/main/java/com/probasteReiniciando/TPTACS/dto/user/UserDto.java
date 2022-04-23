package com.probasteReiniciando.TPTACS.dto.user;

import com.probasteReiniciando.TPTACS.domain.Result;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserDto {
    @NonNull
    private String name;
    private String password;
    private Integer discordId;
    private List<Result> results;
}
