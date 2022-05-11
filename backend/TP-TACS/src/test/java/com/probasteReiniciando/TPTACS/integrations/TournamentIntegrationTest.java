package com.probasteReiniciando.TPTACS.integrations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.repositories.TournamentRepository;
import com.probasteReiniciando.TPTACS.repositories.UserRepositoryInMemory;
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

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TournamentIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // injected with @AutoConfigureMockMvc

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepositoryInMemory userRepository;

    @Test
    public void createTournament() throws Exception {

        TournamentRepository tournamentRepository = new TournamentRepository();

        TournamentService tournamentService = new TournamentService();

        tournamentService.setTournamentRepository(tournamentRepository);

        Date actualDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actualDate);
        calendar.add(Calendar.DATE, 2);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 10);
        Date endDate = calendar.getTime();

        TournamentDto tournamentDtoBody = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

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
    public void obtainPublicTournaments() throws Exception {

        TournamentRepository tournamentRepository = new TournamentRepository();

        TournamentService tournamentService = new TournamentService();

        tournamentService.setTournamentRepository(tournamentRepository);

        Date actualDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actualDate);
        calendar.add(Calendar.DATE, 2);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 10);
        Date endDate = calendar.getTime();

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
                    .perform(post("/tournaments").contentType("application/json").characterEncoding("UTF-8").content(body).requestAttr("userAttributeName","test"))
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

        }

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("offset", "1");
        requestParams.add("limit", "2");


        MvcResult result = mockMvc
                .perform(get("/tournaments").contentType("application/json").params(requestParams))
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

        TournamentRepository tournamentRepository = new TournamentRepository();

        TournamentService tournamentService = new TournamentService();

        tournamentService.setTournamentRepository(tournamentRepository);
        tournamentService.setUserRepository(userRepository);

        User userPepe = User.builder().username("pepe").build();

        when(userRepository.findByName("pepe")).thenReturn(Optional.of(userPepe));

        Date actualDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(actualDate);
        calendar.add(Calendar.DATE, 2);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 10);
        Date endDate = calendar.getTime();

        TournamentDto tournamentDto = TournamentDto.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        mockMvc.perform(post("/tournaments").contentType("application/json").characterEncoding("UTF-8").requestAttr("userAttributeName","test")
                        .content(objectMapper.writeValueAsString(tournamentDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

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
