package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.dao.SessionDAO;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISessionRepository extends MongoRepository<SessionDAO, String> {


    @Query(value = "{'username': ?0}")
    Optional<SessionDAO> findByName(String name);
}
