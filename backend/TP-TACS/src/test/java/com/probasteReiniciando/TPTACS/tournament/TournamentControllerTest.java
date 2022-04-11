package com.probasteReiniciando.TPTACS.tournament;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters  = false)
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc; // injected with @AutoConfigureMockMvc

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TournamentService tournamentService;

    @Test
    public void getPublicTournaments() throws Exception {
        TournamentService tournamentService = mock(TournamentService.class);
        when(tournamentService.getPublicTournaments()).thenReturn(List.of(Tournament.builder().name("TournamentExample").language(Language.ENGLISH).build()));

        MvcResult result = mockMvc
                .perform(get("/tournaments").contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("data");
        TournamentDto[] tournamentDtos = objectMapper.treeToValue(data, TournamentDto[].class);
        List<TournamentDto> tournamentDtosList = new ArrayList<>(Arrays.asList(tournamentDtos));
        Assert.assertEquals(List.of(TournamentDto.builder().name("TournamentExample").language(Language.ENGLISH.name()).build()),tournamentDtosList);

    }

}
