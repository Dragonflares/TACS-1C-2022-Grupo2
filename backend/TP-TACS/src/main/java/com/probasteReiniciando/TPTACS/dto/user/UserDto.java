package com.probasteReiniciando.TPTACS.dto.user;

import com.probasteReiniciando.TPTACS.domain.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserDto {

    @NotEmpty
    private String username;
}
