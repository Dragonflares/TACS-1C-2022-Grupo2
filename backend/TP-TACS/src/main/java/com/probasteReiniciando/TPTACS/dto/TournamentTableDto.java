package com.probasteReiniciando.TPTACS.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class TournamentTableDto {private String id;

    @NonNull
    private  String name;

    @NonNull
    private Language language;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder.Default
    private Privacy privacy = Privacy.PUBLIC;

    private boolean isOwner;
}
