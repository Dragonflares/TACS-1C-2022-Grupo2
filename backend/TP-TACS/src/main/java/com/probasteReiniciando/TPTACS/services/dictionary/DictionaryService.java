package com.probasteReiniciando.TPTACS.services.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
@Service
public class DictionaryService {

    @Autowired
    private  WordFinder wordFinder;

    public WordDto findWord(String name,String language) throws JsonProcessingException {
        return WordDto.builder().definition(wordFinder.findWord(name,language).orElseThrow()).build();
    }

}
