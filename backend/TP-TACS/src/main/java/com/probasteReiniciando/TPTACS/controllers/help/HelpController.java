package com.probasteReiniciando.TPTACS.controllers.help;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.HelpDto;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.functions.WordFileSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelpController {

    @Autowired
    WordFileSearcher wordFileSearcher;

    @PostMapping(path="/help", produces = "application/json")
    public List<WordDto> help(@RequestBody HelpDto helpDto) throws JsonProcessingException {
        List<String> words =  wordFileSearcher.readWordsFromFile(helpDto.getLanguage());
        return wordFileSearcher.findWords(helpDto,words);
    }
}
