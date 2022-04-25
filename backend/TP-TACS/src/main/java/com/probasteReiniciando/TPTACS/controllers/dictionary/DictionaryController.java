package com.probasteReiniciando.TPTACS.controllers.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.services.dictionary.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @GetMapping(path="/dictionary", produces = "application/json")
    public WordDto wordDefinition(@RequestParam String word, @RequestParam String language) throws JsonProcessingException {
        return dictionaryService.findWord(word,language);
    }


}
