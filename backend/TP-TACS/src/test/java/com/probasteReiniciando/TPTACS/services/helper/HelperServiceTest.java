package com.probasteReiniciando.TPTACS.services.helper;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.HelpDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc(addFilters  = false)
public class HelperServiceTest {


    @Test
    public void readWordsFromEnglishText () throws Exception {

        HelperService helperService = new HelperService();
        Assert.assertTrue(helperService.readWordsFromFile(Language.ENGLISH).size() > 0);
    }


    @Test
    public void readWordsFromSpanishText () throws Exception {

        HelperService helperService = new HelperService();
        Assert.assertTrue(helperService.readWordsFromFile(Language.SPANISH).size() > 0);
    }


    @Test
    public void validatesGreenWords () throws Exception {

        HelperService helperService = new HelperService();
        HashMap<Integer,String> chars = new HashMap<>();
        chars.put(0,"a");
        chars.put(1,"o");
        List<String> words = new ArrayList<>();
        words.add("aoli");
        Assert.assertEquals(helperService.findWords(HelpDto.builder().greenWords(chars).build(),words).get(0).getPhrase(),words.get(0));
    }


    @Test
    public void validatesYellowWords () throws Exception {

        HelperService helperService = new HelperService();
        HashMap<Integer,String> chars = new HashMap<>();
        chars.put(0,"a");
        chars.put(1,"o");
        List<String> words = new ArrayList<>();
        words.add("aoli");
        Assert.assertEquals(helperService.findWords(HelpDto.builder().greenWords(chars).yellowWords("i").build(),words).get(0).getPhrase(),words.get(0));
    }


    @Test
    public void validatesGreyWords () throws Exception {

        HelperService helperService = new HelperService();
        HashMap<Integer,String> chars = new HashMap<>();
        chars.put(0,"a");
        chars.put(1,"o");
        List<String> words = new ArrayList<>();
        words.add("zorro");
        words.add("aoli");
        Assert.assertEquals(helperService.findWords(HelpDto.builder().greenWords(chars).greyWords("z").build(),words).get(0).getPhrase(),words.get(1));
    }



}
