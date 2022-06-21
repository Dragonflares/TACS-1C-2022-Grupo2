package com.probasteReiniciando.TPTACS.integrations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.repositories.IUserRepositoryMongoDB;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TournamentIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // injected with @AutoConfigureMockMvc

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUserRepositoryMongoDB userRepositoryInMemory;

    @Before
    public void setup() {
        userRepositoryInMemory.deleteAll();

    }
/*
    //TODO
    @Test
    public void createTournament() throws Exception {

        userRepositoryInMemory.save(User.builder().username("test").password("test").id(5584898).build());


        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto tournamentDtoBody = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        String body = objectMapper.writeValueAsString(tournamentDtoBody);

        MvcResult result = mockMvc
                .perform(post("/api/tournaments").contentType("application/json").characterEncoding("UTF-8").content(body).requestAttr("userAttributeName","test"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto tournamentDtoResponse = objectMapper.treeToValue(data, TournamentDto.class);

        Assert.assertEquals(tournamentDtoBody.getName(), tournamentDtoResponse.getName());

    }

    //TODO

    @Test
    public void obtainPublicTournaments() throws Exception {

        userRepositoryInMemory.save(User.builder().username("test").password("test").id(5584898).build());

        TournamentRepositoryInMemory tournamentRepository = new TournamentRepositoryInMemory();

        TournamentService tournamentService = new TournamentService(tournamentRepository,null);

        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto tournamentDtoBody1 = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        TournamentDto tournamentDtoBody2 = TournamentDto.builder()
                .name("Libertadores Wordle")
                .language(Language.SPANISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        TournamentDto tournamentDtoBody3 = TournamentDto.builder()
                .name("Liga Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        TournamentDto tournamentDtoBody4 = TournamentDto.builder()
                .name("Pepe Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PRIVATE).build();


        List<TournamentDto> tournamentDtoBodys = List.of(tournamentDtoBody1, tournamentDtoBody2, tournamentDtoBody3, tournamentDtoBody4);

        for (TournamentDto tournamentBody : tournamentDtoBodys) {

            String body = objectMapper.writeValueAsString(tournamentBody);

            MvcResult result = mockMvc
                    .perform(post("/api/tournaments").contentType("application/json").characterEncoding("UTF-8").content(body).requestAttr("userAttributeName","test"))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

        }

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "1");
        requestParams.add("limit", "2");


        MvcResult result = mockMvc
                .perform(get("/api/tournaments").contentType("application/json").params(requestParams).requestAttr("userAttributeName","test"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto[] tournamentDtos = objectMapper.treeToValue(data, TournamentDto[].class);

        Assert.assertEquals(tournamentDtos.length, 2);

    }



    @Test
    public void addUserToPublicTournament() throws Exception {

        userRepositoryInMemory.save(User.builder().username("test").password("test").id(5584898).build());

        userRepositoryInMemory.save(User.builder().username("pepe").password("pepe").id(777895663).build());

        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = startDate.plusDays(10);

        TournamentDto tournamentDto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        MvcResult resultId =mockMvc.perform(post("/api/tournaments").contentType("application/json").characterEncoding("UTF-8").requestAttr("userAttributeName","test")
                        .content(objectMapper.writeValueAsString(tournamentDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNodeId = objectMapper.readTree(resultId.getResponse().getContentAsString());
        Integer idTournament = jsonNodeId.get("response").get("id").asInt();

        UserDto user = UserDto.builder().username("pepe").build();

        String body = objectMapper.writeValueAsString(user);

        MvcResult result = mockMvc
                .perform(post("/api/tournaments/"+idTournament+"/participants").contentType("application/json").characterEncoding("UTF-8").requestAttr("userAttributeName","test").content(body))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        UserDto[] myObjects = objectMapper.convertValue(data, UserDto[].class);

        List<UserDto> usernames = Arrays.asList(myObjects);
        List<UserDto> usernamesExpected = new ArrayList<>();
        usernamesExpected.add(UserDto.builder().username("test").build());
        usernamesExpected.add(UserDto.builder().username("pepe").build());
        Assert.assertEquals(usernames,usernamesExpected);

    }
*/
}
