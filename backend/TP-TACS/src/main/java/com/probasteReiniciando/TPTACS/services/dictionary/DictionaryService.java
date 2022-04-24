package com.probasteReiniciando.TPTACS.services.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class DictionaryService {

    private  WordFinder wordFinder;

    public  DictionaryService(WordFinder wordFinder) {
        this.wordFinder = wordFinder;
    }

    public WordDto findWord(String name,String language) throws JsonProcessingException {
        return WordDto.builder().phrase(wordFinder.findWord(name,language).orElseThrow()).build();
    }

}
