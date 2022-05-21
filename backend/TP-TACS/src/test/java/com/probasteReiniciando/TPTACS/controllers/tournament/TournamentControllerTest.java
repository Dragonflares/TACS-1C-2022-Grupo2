package com.probasteReiniciando.TPTACS.controllers.tournament;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.*;
import com.probasteReiniciando.TPTACS.dto.PositionDto;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;

import static com.probasteReiniciando.TPTACS.domain.Privacy.PUBLIC;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TournamentService tournamentService;

    private final ModelMapperTacs mapper = new ModelMapperTacs();

    @Test
    public void getPublicTournaments() throws Exception {

        List<Tournament> expectedTournaments = List.of(Tournament.builder().name("TournamentExample").language(Language.ENGLISH).build());

        when(tournamentService.obtainTournaments(1, 5, Privacy.PUBLIC, "PEPE")).
                thenReturn(expectedTournaments);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("offset", "1");
        requestParams.add("limit", "5");


        MvcResult result = mockMvc
                .perform(get("/tournaments").contentType("application/json").params(requestParams).requestAttr("userAttributeName","PEPE"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        List<TournamentDto> actualTournaments = new ArrayList<>(Arrays.asList(objectMapper.treeToValue(data, TournamentDto[].class)));

        Assertions.assertEquals(expectedTournaments, mapper.mapList(actualTournaments, Tournament.class));

    }

    @Test
    public void getIndividualTournament() throws Exception {

        Tournament expectedTournament = Tournament.builder().name("TournamentExample").language(Language.ENGLISH).privacy(PUBLIC).build();

        when(tournamentService.getTournamentById(5)).thenReturn(expectedTournament);

        MvcResult result = mockMvc
                .perform(get("/tournaments/5").contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        List<TournamentDto> actualTournaments = new ArrayList<>(List.of(objectMapper.treeToValue(data, TournamentDto.class)));


        Assertions.assertEquals(List.of(expectedTournament), mapper.mapList(actualTournaments, Tournament.class));

    }

    @Test
    public void createTournament() throws Exception {

        Tournament expectedTournament = Tournament.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .privacy(Privacy.PUBLIC).build();

        when(tournamentService.createTournament(expectedTournament,"pepe")).thenReturn(expectedTournament);

        String body = objectMapper.writeValueAsString(mapper.map(expectedTournament,TournamentDto.class));

        MvcResult result = mockMvc
                .perform(
                        post("/tournaments")
                                .contentType("application/json")
                                .characterEncoding("UTF-8")
                                .content(body)
                                .requestAttr("userAttributeName","pepe")
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto actualTournament = objectMapper.treeToValue(data, TournamentDto.class);

        Assertions.assertEquals(expectedTournament, mapper.map(actualTournament, Tournament.class));

    }

    @Test
    public void addUserToPublicTournament() throws Exception {

        User expectedParticipant = User.builder().username("pepe").build();

        List<User> expectedParticipants = List.of(expectedParticipant);

        when(tournamentService.addUser(1, expectedParticipant.getUsername(),"owner")).thenReturn(expectedParticipants);

        String body = objectMapper.writeValueAsString(mapper.map(expectedParticipant,UserDto.class));

        MvcResult result = mockMvc
                .perform(post("/tournaments/1/participants").contentType("application/json").characterEncoding("UTF-8").content(body)
                        .requestAttr("userAttributeName","owner"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        List<UserDto> actualParticipants = new ArrayList<>(Arrays.asList(objectMapper.treeToValue(data, UserDto[].class)));

        Assertions.assertEquals(expectedParticipants, mapper.mapList(actualParticipants,User.class));

    }

    @Test
    public void obtainPositions() throws Exception {

        User user1 = User.builder().username("pepe").build();
        User user2 = User.builder().username("juan").build();
        Position position1 = Position.builder().points(120).user(user1).build();
        Position position2 = Position.builder().points(240).user(user2).build();
        List<Position> positionsExpected = List.of(position1,position2);
        when(tournamentService.obtainPositions(1, Optional.empty())).thenReturn(positionsExpected);

        MvcResult result = mockMvc
                .perform(get("/tournaments/1/positions").contentType("application/json").characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        PositionDto[] positionsDto = objectMapper.treeToValue(data, PositionDto[].class);
        List<PositionDto> actualPositions = new ArrayList<>(Arrays.asList(positionsDto));

        Assertions.assertEquals(mapper.mapList(positionsExpected,PositionDto.class), actualPositions);

    }


    @Test
    public void obtainParticipants() throws Exception {

        List<User> participantsExpected = List.of(User.builder().username("pepe").build(),User.builder().username("juan").build());

        when(tournamentService.obtainParticipants(1, Optional.empty(), Optional.empty())).thenReturn(participantsExpected);

        MvcResult result = mockMvc
                .perform(get("/tournaments/1/participants").contentType("application/json").characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        List<UserDto> actualParticipants = new ArrayList<>(Arrays.asList(objectMapper.treeToValue(data, UserDto[].class)));

        Assertions.assertEquals(mapper.mapList(participantsExpected,UserDto.class), actualParticipants);

    }

    @Test
    public void updateTournament () throws Exception {

        Tournament expectedTournament = Tournament.builder()
                .name("Champions Wordle")
                .language(Language.ENGLISH)
                .privacy(Privacy.PUBLIC).build();

        when(tournamentService.updateTournament(1, expectedTournament, "pepe")).thenReturn(expectedTournament);

        String body = objectMapper.writeValueAsString(mapper.map(expectedTournament,TournamentDto.class));

        MvcResult result = mockMvc
                .perform(
                        patch("/tournaments/1")
                                .contentType("application/json")
                                .characterEncoding("UTF-8")
                                .content(body)
                                .requestAttr("userAttributeName","pepe")
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto actualTournament = objectMapper.treeToValue(data, TournamentDto.class);

        Assertions.assertEquals(expectedTournament, mapper.map(actualTournament,Tournament.class));


    }


}
