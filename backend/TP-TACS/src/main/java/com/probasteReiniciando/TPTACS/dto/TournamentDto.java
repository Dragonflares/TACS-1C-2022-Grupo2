package com.probasteReiniciando.TPTACS.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class TournamentDto {

    @NonNull
    private String name;
    @NonNull
    private Language language;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    private Privacy privacy;

    private UserDto owner;

    private List<UserDto> participants;

    private List<PositionDto> positions;

}
