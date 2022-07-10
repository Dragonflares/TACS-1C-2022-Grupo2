package com.probasteReiniciando.TPTACS.controllers.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.HelpDto;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.services.dictionary.DictionaryService;
import com.probasteReiniciando.TPTACS.services.helper.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class HelperController {

    @Autowired
    HelperService helperService;

    @Autowired
    DictionaryService dictionaryService;

    @PostMapping(path="/help", produces = "application/json")
    public List<WordDto> help(@RequestBody HelpDto helpDto) throws JsonProcessingException {
        List<WordDto> words = helperService.wordSearch(helpDto);

        if(helpDto.getFromDictionary()){
            words = words.stream().filter(word -> {
                try {
                    return  dictionaryService.exist(word.getPhrase(), Language.getLanguage(helpDto.getLanguage()));
                } catch (JsonProcessingException e) {
                    return false;
                }
            }).toList();
        }

        return words;
    }
}
