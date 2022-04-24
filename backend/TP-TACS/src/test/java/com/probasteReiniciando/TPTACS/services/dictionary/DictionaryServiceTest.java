package com.probasteReiniciando.TPTACS.services.dictionary;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.services.helper.HelperService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc(addFilters  = false)
public class DictionaryServiceTest {

    @Autowired
    WebWordFinder webWordFinder;

    @Test
    public void getWordfromApiWebWordFinder () throws Exception {

        DictionaryService dictionaryService = new DictionaryService(webWordFinder);
        WordDto wordDto = dictionaryService.findWord("house","en");
        Assert.assertTrue(wordDto.getPhrase() != null);
    }
}
