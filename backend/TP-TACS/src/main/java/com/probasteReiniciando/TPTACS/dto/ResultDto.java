package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ResultDto {

    private  Integer id;

    private  String username;

    @Min(value = 1, message = "The value must be positive")
    @Max(value = 7, message = "The value must be under 7")
    private  Integer points;

    private String language;

    private LocalDate date;

}
