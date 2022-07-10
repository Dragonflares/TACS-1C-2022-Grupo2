package com.probasteReiniciando.TPTACS.services.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.dao.WordScoreDAO;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.exceptions.DictionaryBadRequestException;
import com.probasteReiniciando.TPTACS.exceptions.WordNotFoundException;
import com.probasteReiniciando.TPTACS.repositories.IWordScoreRepositoryMongoDB;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class DictionaryService {

    private  WordFinder wordFinder;

    final private IWordScoreRepositoryMongoDB wordScoreRepository;

    final private ModelMapperTacs modelMapper = new ModelMapperTacs();

    public  DictionaryService(WordFinder wordFinder, IWordScoreRepositoryMongoDB wordScoreRepository) {
        this.wordFinder = wordFinder;
        this.wordScoreRepository = wordScoreRepository;
    }

    public WordDto findWord(String name,String language) throws JsonProcessingException {

        WordDto wordDto =  WordDto.builder().phrase(wordFinder.findWord(name,language).orElseThrow(() -> new WordNotFoundException(name))).build();

        Optional<WordScoreDAO> optionalWordScore = wordScoreRepository.obtainWordScore(name, Language.getValueOfCode(language));

        WordScoreDAO wordScore;

        if(optionalWordScore.isPresent()){
            wordScore = optionalWordScore.get();
            wordScore.setScore(wordScore.getScore().intValue()+1);
        } else {
            wordScore = WordScoreDAO.builder().word(name).score(1).language(Language.getValueOfCode(language)).build();
        }

        wordScoreRepository.save(wordScore);

        return wordDto;
    }

    public Page<WordScoreDAO> obtainWords (Language language, String orderBy, int page, int limit) {

        if("SCORE".equals(orderBy)) {
            return wordScoreRepository.obtainWordsOrderByScore(language, PageRequest.of(page - 1, limit));
        } else {
            throw new DictionaryBadRequestException("Order by " + orderBy + " not supported");
        }

    }

}
