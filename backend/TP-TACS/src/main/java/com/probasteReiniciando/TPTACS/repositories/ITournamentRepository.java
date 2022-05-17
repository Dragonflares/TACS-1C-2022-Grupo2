package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITournamentRepository {

    List<Tournament> obtainPublicTournaments(int offset, int limit);

    Optional<Tournament> obtainTournament(int id);

    List<Result> obtainResults(int id);

    Tournament createTournament(Tournament tournament);

    List<String> addUser(Tournament tournament, User user);

    List<String> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order);

    void updateTournament(int tournamentId, Tournament tournament);

    List<Tournament> findByOwner(String owner, int page, int limit);

}
