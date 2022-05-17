package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ResultDto {

    private  String username;

    private  Integer points;

    private String language;

    private LocalDate date;

}
