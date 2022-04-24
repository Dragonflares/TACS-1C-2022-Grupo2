package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;

import java.util.List;
import java.util.Optional;

public interface ITournamentRepository {

    List<Tournament> obtainPublicTournaments(int offset, int limit);

    Optional<Tournament> obtainTournament(int id);

    List<Result> obtainResults(int id);

    Tournament createTournament(Tournament tournament);

    List<String> addUser(Tournament tournament, User user);

}
