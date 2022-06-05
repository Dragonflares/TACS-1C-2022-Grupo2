package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITournamentRepository {

    Integer quantityOfPublicTournaments();

    Integer quantityOfPrivateTournaments(String username);

    List<Tournament> obtainPublicTournaments(int page, int limit);

    List<Tournament> obtainPrivateTournaments(int page, int limit, String username);

    Optional<Tournament> obtainTournament(int id);

    List<Result> obtainResults(int id);

    void addUser(int tournamentId, User user);

    List<User> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order);

    void updateTournament(int tournamentId, Tournament tournament);

    List<Tournament> findByOwner(String owner, int page, int limit);

}
