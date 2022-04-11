package com.probasteReiniciando.TPTACS.functions;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class TournamentConverter {

    //TODO create user converter and complete fields missing
    public TournamentDto convertTournamentToDto(Tournament tournament) {
        return TournamentDto.builder()
                .name(tournament.getName())
                .language(tournament.getLanguage().name())
                .startDate(tournament.getStartDate())
                .endDate(tournament.getEndDate())
                .privacy(tournament.getPrivacy())
                .build();
    }

    public Tournament convertDtoToTournament(TournamentDto dto) {
        return Tournament.builder()
                .name(dto.getName())
                .language(Language.valueOf(dto.getLanguage()))
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .privacy(dto.getPrivacy())
                .build();
    }

    public List<TournamentDto> convertListTournamentToDto(List<Tournament> tournamentList) {

        List<TournamentDto> tournamentDtos = new ArrayList<>();
        tournamentList.forEach(tournament -> tournamentDtos.add(convertTournamentToDto(tournament)));
        return tournamentDtos;
    }



}
