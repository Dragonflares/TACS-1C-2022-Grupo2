package com.probasteReiniciando.TPTACS.repositories;


import com.probasteReiniciando.TPTACS.dao.TournamentDAO;
import com.probasteReiniciando.TPTACS.dao.WordScoreDAO;
import com.probasteReiniciando.TPTACS.domain.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IWordScoreRepositoryMongoDB extends MongoRepository<WordScoreDAO, String>  {

    @Query(value = "{'word': ?0, 'language': ?1}")
    Optional<WordScoreDAO> obtainWordScore(String word, Language language);

    @Query(value = "{'language': ?0}", sort = "{'score': -1}")
    Page<WordScoreDAO> obtainWordsOrderByScore(Language language, Pageable pageable);

}
