package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.dao.ResultDAO;
import com.probasteReiniciando.TPTACS.dao.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepositoryMongoDB extends MongoRepository<UserDAO, String>  {


    @Query(value = "{'username': ?0}")
    Optional<UserDAO> findByName(String name);


    @Query(value = "{ 'username' : ?0, 'resultDAOS' : { $elemMatch : { 'language' : ?1, 'date': ?2 } } }",
    fields = "{ 'resultDAOS' : 1 }", exists = true)
    boolean existResultToday(String logedUser, String Language, String date);

    @Query(value = "{'id': ?0}")
    Optional<UserDAO> findById(int id);




}
