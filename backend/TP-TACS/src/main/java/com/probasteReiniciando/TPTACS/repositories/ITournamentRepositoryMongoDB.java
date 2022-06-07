package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.dao.TournamentDAO;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITournamentRepositoryMongoDB extends MongoRepository<TournamentDAO, String> {

    @Query(value = "{'privacy': 'PUBLIC'}", count = true)
    Integer quantityOfPublicTournaments();

    @Query(value = "{'privacy': 'PRIVATE'}", count = true)
    Integer quantityOfPrivateTournaments(String username);

    @Query(value = "{'id': ?0}")
    Optional<TournamentDAO> obtainTournament(String id);

    @Query(value = "{'id': ?0}", fields = "{ 'results' : 1}")
    List<Result> obtainResults(String id);

    @Query(value = "{'id': ?0}")
        //@Update("{ '$push' : { 'participants' : ?1 } }")
    void addUser(String tournamentId, User user);

    @Query(value = "{'id': ?0}", fields = "{ 'participants' : 1}")
    List<UserDAO> obtainParticipants(String tournamentId);

    //TODO
    //void updateTournament(int tournamentId, Tournament tournament);

    //TODO
    @Query(value = "{'owner': ?0}")
    List<TournamentDAO> findByOwner(String owner, int page, int limit);

    @Query(value = "{'privacy': 'PUBLIC', 'participants': { $not : { $elemMatch : { 'username' : ?0 } }  }}")
    Page<TournamentDAO> obtainPublicTournaments(String username, Pageable pageable);

    @Query(value = "{'participants': { $elemMatch : { 'username' : ?0 }  } }")
    Page<TournamentDAO> obtainPrivateTournaments(String username, Pageable pageable);



}
