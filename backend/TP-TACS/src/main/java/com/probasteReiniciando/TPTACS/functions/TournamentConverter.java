package com.probasteReiniciando.TPTACS.functions;

import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class TournamentConverter {

    //TODO create user converter and complete fields missing
    public TournamentDto convertTournamentToDto(Tournament tournament) {
        return TournamentDto.builder()
                .name(tournament.getName())
                .language(tournament.getLanguage())
                .startDate(tournament.getStartDate())
                .endDate(tournament.getEndDate())
                .privacy(tournament.getPrivacy())
                .build();
    }

    public Tournament convertDtoToTournament(TournamentDto dto) {
        return Tournament.builder()
                .name(dto.getName())
                .language(dto.getLanguage())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .privacy(dto.getPrivacy())
                .build();
    }

}
