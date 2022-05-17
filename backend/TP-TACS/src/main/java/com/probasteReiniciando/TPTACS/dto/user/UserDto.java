package com.probasteReiniciando.TPTACS.dto.user;

import com.probasteReiniciando.TPTACS.domain.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserDto {

    private String username;
    private List<Result> results;
}
