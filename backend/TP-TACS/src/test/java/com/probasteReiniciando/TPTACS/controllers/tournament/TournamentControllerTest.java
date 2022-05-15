package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.Tournament;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc; // injected with @AutoConfigureMockMvc

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TournamentService tournamentService;

    @Test
    public void getPublicTournaments() throws Exception {
        when(tournamentService.obtainPublicTournaments(1, 5)).thenReturn(List.of(Tournament.builder().name("TournamentExample").language(Language.ENGLISH).build()));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("offset", "1");
        requestParams.add("limit", "5");


        MvcResult result = mockMvc
                .perform(get("/tournaments").contentType("application/json").params(requestParams))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto[] tournamentDtos = objectMapper.treeToValue(data, TournamentDto[].class);
        List<TournamentDto> tournamentDtosList = new ArrayList<>(Arrays.asList(tournamentDtos));
        Assert.assertEquals(List.of(TournamentDto.builder().name("TournamentExample").language(Language.ENGLISH).build()), tournamentDtosList);

    }

    @Test
    public void getIndividualTournament() throws Exception {

        when(tournamentService.getTournamentById(5)).thenReturn(Tournament.builder().name("TournamentExample").language(Language.ENGLISH).build());

        MvcResult result = mockMvc
                .perform(get("/tournaments/5").contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto tournamentDto = objectMapper.treeToValue(data, TournamentDto.class);
        List<TournamentDto> tournamentDtosList = new ArrayList<>(Arrays.asList(tournamentDto));
        Assert.assertEquals(List.of(TournamentDto.builder().name("TournamentExample").language(Language.ENGLISH).build()), tournamentDtosList);

    }

    @Test
    public void createTournament() throws Exception {

        TournamentDto tournamentDtoBody = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .privacy(Privacy.PUBLIC).build();

        when(tournamentService.createTournament(tournamentDtoBody,"pepe")).thenReturn(tournamentDtoBody);

        String body = objectMapper.writeValueAsString(tournamentDtoBody);

        MvcResult result = mockMvc
                .perform(post("/tournaments").contentType("application/json").characterEncoding("UTF-8").content(body).requestAttr("userAttributeName","test"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto tournamentDtoResponse = objectMapper.treeToValue(data, TournamentDto.class);

        Assert.assertEquals(tournamentDtoBody.getName(), tournamentDtoResponse.getName());

    }

    @Test
    public void addUserToPublicTournament() throws Exception {

        when(tournamentService.addUser(1, "pepe","duenio")).thenReturn(List.of("pepe"));

        UserDto user = UserDto.builder().username("pepe").build();

        String body = objectMapper.writeValueAsString(user);

        MvcResult result = mockMvc
                .perform(patch("/tournaments/1/participants").contentType("application/json").characterEncoding("UTF-8").content(body))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        String[] usernames = objectMapper.treeToValue(data, String[].class);
        List<String> usernamesList = new ArrayList<>(Arrays.asList(usernames));
        Assert.assertEquals(List.of("pepe"), usernamesList);

    }

}
