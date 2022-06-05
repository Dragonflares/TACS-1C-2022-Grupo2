package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITournamentRepositoryMongoDB extends ITournamentRepository, MongoRepository<Tournament, Integer> {

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

    @Override
    @Query(value = "'id': ?0", fields = "{ 'results' : 1}")
    List<Result> obtainResults(int id);

    //TODO
    @Override
    Tournament createTournament(Tournament tournament);

    @Override
    @Query(value = "'id': ?0")
    @Update("{ '$push' : { 'participants' : ?1 } }")
    void addUser(int tournamentId, User user);

    @Override
    @Query(value = "'id': ?0", fields = "{ 'participants' : 1}")
    List<User> obtainParticipants(int tournamentId, Optional<String> orderBy, Optional<String> order);

    //TODO
    @Override
    @Update()
    void updateTournament(int tournamentId, Tournament tournament);

    //TODO
    @Override
    List<Tournament> findByOwner(String owner, int page, int limit);

}
