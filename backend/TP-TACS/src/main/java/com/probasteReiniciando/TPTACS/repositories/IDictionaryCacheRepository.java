package com.probasteReiniciando.TPTACS.repositories;

import com.probasteReiniciando.TPTACS.dao.DictionaryCacheDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDictionaryCacheRepository extends CrudRepository<DictionaryCacheDao, String> {

}


