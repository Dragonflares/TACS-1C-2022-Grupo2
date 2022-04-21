package com.probasteReiniciando.TPTACS.controllers.dictionary;

import com.probasteReiniciando.TPTACS.services.dictionary.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictionaryController {

    @Autowired
    DictionaryService dictionary;

    @GetMapping(path="/dictionary", produces = "application/json")
    public String wordDefinition(@RequestParam String word) {

        return dictionary.getWordFinder().findWord(word);
    }


}
