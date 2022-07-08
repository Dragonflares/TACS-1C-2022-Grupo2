package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.dao.TokenDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends CrudRepository<TokenDao, String> {

}


