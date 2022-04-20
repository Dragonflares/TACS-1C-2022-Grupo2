package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionDto {

    private UserDto user;
    private Integer points;
}
