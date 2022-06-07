package com.probasteReiniciando.TPTACS.repositories;


import com.probasteReiniciando.TPTACS.dao.ResultDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IResultRepositoryMongoDB extends MongoRepository<ResultDAO, String> {


}
