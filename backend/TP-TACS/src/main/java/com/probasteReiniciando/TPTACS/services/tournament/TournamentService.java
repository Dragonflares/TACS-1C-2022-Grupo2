package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    @Autowired
    private ITournamentRepository tournamentRepository;

    public List<Tournament> getPublicTournaments() {
        return tournamentRepository.getPublicTournaments();
    }
}
