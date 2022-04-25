package com.probasteReiniciando.TPTACS.controllers.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.dto.HelpDto;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.services.helper.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class HelperController {

    @Autowired
    HelperService helperService;

    @PostMapping(path="/helper", produces = "application/json")
    public List<WordDto> help(@RequestBody HelpDto helpDto) throws JsonProcessingException {
        List<String> words =  helperService.readWordsFromFile(helpDto.getLanguage());
        return helperService.findWords(helpDto,words);
    }
}
