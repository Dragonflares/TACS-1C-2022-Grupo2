package com.probasteReiniciando.TPTACS.services.dictionary;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.exceptions.WordNotFoundException;
import com.probasteReiniciando.TPTACS.services.helper.HelperService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters  = false)
public class DictionaryServiceTest {

    @MockBean
    WordFinder webWordFinder;

    @Test
    public void findWord () throws Exception {

        String definition = "a building for human habitation, especially one that consists of a ground floor and one or more upper storeys.";

        when(webWordFinder.findWord("house","en")).thenReturn(Optional.of(definition));

        DictionaryService dictionaryService = new DictionaryService(webWordFinder);
        WordDto wordDto = dictionaryService.findWord("house","en");
        Assert.assertTrue(wordDto.getPhrase() != null);

    }

    @Test
    public void findWordThrowsWordNotFoundException () throws Exception {

        String definition = null;

        String name = "house";

        String language = "en";

        when(webWordFinder.findWord(name,language)).thenReturn(Optional.ofNullable(definition));

        DictionaryService dictionaryService = new DictionaryService(webWordFinder);

        Assertions.assertThrowsExactly(WordNotFoundException.class, () -> dictionaryService.findWord(name,language));

    }

}
