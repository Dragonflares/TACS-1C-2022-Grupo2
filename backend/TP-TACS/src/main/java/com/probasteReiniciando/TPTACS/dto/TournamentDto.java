package com.probasteReiniciando.TPTACS.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class TournamentDto {

    private Integer id;

    @NonNull
    private  String name;

    @NonNull
    private Language language;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private  Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private  Date endDate;

    private  Privacy privacy;

    private  UserDto owner;
    // Podria ser un string nomas porque en el DTO iria lo que a vos te interesa que le llegue al front,
    // asi que con el nombre alcanza

    private  List<UserDto> participants;

    private  List<PositionDto> positions;

}