package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.domain.Language;
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


    private  Integer points;

    private Language language;

    private LocalDate date;

}
