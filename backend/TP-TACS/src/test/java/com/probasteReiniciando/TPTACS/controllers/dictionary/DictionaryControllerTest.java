package com.probasteReiniciando.TPTACS.controllers.dictionary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.HelpDto;
import com.probasteReiniciando.TPTACS.dto.WordDto;
import com.probasteReiniciando.TPTACS.services.dictionary.DictionaryService;
import com.probasteReiniciando.TPTACS.services.helper.HelperService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters  = false)
public class DictionaryControllerTest {


    @MockBean
    DictionaryService dictionaryService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void helperTest() throws Exception {

        List<WordDto> wordsTest = new ArrayList<>();
        wordsTest.add(WordDto.builder().phrase("test").build());

        when(dictionaryService.findWord("test", "en")).thenReturn(WordDto.builder().phrase("test").build());

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("word", "test");
        requestParams.add("language", "ENGLISH");


        MvcResult result = mockMvc
                .perform(get("/dictionary").contentType("application/json").params(requestParams))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        WordDto word = objectMapper.treeToValue(data, WordDto.class);
        Assert.assertEquals(WordDto.builder().phrase("test").build(),word);
        verify(dictionaryService).findWord("test", "en");

    }


}
