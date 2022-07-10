package com.probasteReiniciando.TPTACS.controllers.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.LanguagesDto;
import com.probasteReiniciando.TPTACS.dto.PagedListDto;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.dto.WordScoreDto;
import com.probasteReiniciando.TPTACS.exceptions.DictionaryBadRequestException;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.exceptions.WordNotFoundException;
import com.probasteReiniciando.TPTACS.services.dictionary.DictionaryService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    private ModelMapperTacs modelMapper;

    @GetMapping(path="/meanings", produces = "application/json")
    public WordDto wordDefinition(@RequestParam String word, @RequestParam String language) throws  JsonProcessingException {
        return dictionaryService.findWord(word,Language.getLanguage(validateLanguage(language)));
    }

    @GetMapping(path="/languages", produces = "application/json")
    public LanguagesDto getLanguages() throws  JsonProcessingException {
        return LanguagesDto.builder().languages(Arrays.asList(Language.values())).build();
    }

    private Language validateLanguage(String language) {
        if(!EnumUtils.isValidEnum(Language.class, language)) {
            String messageError = String.format("%s is not a valid language or is not supported", language);
            throw new DictionaryBadRequestException(messageError);
        }
        return Language.valueOf(language);
    }

    @GetMapping(path="/words", produces = "application/json")
    private PagedListDto<WordScoreDto> obtainWords(@RequestParam Language language,@RequestParam String orderBy, @RequestParam(defaultValue = "1")  int page,
                                                   @RequestParam(defaultValue = "5") int limit) {

        var words = dictionaryService.obtainWords(language,orderBy,page,limit);

        return  modelMapper.map(
                new PagedListDto<WordScoreDto>(
                        modelMapper.mapList(words.getContent(), WordScoreDto.class),
                        words.getTotalElements()
                ),
                PagedListDto.class);

    }


}
