package com.probasteReiniciando.TPTACS.services.tournament;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentNotFoundException;
import com.probasteReiniciando.TPTACS.repositories.ITournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    @Autowired
    private ITournamentRepository tournamentRepository;

    public Tournament postTournament(TournamentDto dto){
        return tournamentRepository.createTournament(dto);
    }

    public List<Tournament> obtainPublicTournaments() {
        return tournamentRepository.obtainPublicTournaments();
    }

    public Tournament getTournamentById(int id) throws TournamentNotFoundException {
        return tournamentRepository.obtainTournament(id).orElseThrow(() -> new TournamentNotFoundException(String.valueOf(id)));
    }

    public List<Result> getResults(){
        return tournamentRepository.obtainResults();
    }

}
