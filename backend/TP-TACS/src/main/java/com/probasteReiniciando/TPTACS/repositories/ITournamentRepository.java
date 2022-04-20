package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;

import java.util.List;

public interface ITournamentRepository {
    List<Tournament> obtainPublicTournaments();
    Tournament obtainTournament(int id);
    List<Result> obtainResults();
    Tournament returnTournament(TournamentDto dto);
}
