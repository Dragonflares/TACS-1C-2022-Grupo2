package com.probasteReiniciando.TPTACS.repositories;


import com.probasteReiniciando.TPTACS.dao.ResultDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResultRepositoryMongoDB extends MongoRepository<ResultDAO, String> {



}
