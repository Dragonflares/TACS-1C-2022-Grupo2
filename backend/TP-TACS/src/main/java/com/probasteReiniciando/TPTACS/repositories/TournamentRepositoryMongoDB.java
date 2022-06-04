package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TournamentRepositoryMongoDB extends ITournamentRepository, MongoRepository<Tournament, Integer> {

    @Override
    @Query(value = "'privacy': 'PUBLIC'", count = true)
    Integer quantityOfPublicTournaments();

    @Override
    @Query(value = "'privacy': 'PRIVATE'", count = true)
    Integer quantityOfPrivateTournaments(String username);

    @Override
    //TODO
    List<Tournament> obtainPublicTournaments(int page, int limit);

    @Override
    //TODO
    List<Tournament> obtainPrivateTournaments(int page, int limit, String username);

    @Override
    @Query(value = "'id': ?0")
    Optional<Tournament> obtainTournament(int id);

    //TODO
    @Override
    List<Result> obtainResults(int id);

    //TODO
    @Override
    Tournament createTournament(Tournament tournament);

    //TODO
    @Override
    void addUser(Tournament tournament, User user);

    //TODO
    @Override
    List<User> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order);

    //TODO
    @Override
    void updateTournament(int tournamentId, Tournament tournament);

    //TODO
    @Override
    List<Tournament> findByOwner(String owner, int page, int limit);

}
