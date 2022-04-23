package com.probasteReiniciando.TPTACS.integrations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.dto.TournamentDto;
import com.probasteReiniciando.TPTACS.exceptions.TournamentBadRequestException;
import com.probasteReiniciando.TPTACS.repositories.TournamentRepository;
import com.probasteReiniciando.TPTACS.services.tournament.TournamentService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters  = false)
public class TournamentIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // injected with @AutoConfigureMockMvc

    @Autowired
    private ObjectMapper objectMapper;

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
                .language(Language.ENG)
                .startDate(startDate).endDate(endDate)
                .privacy(Privacy.PUBLIC).build();

        String body = objectMapper.writeValueAsString(tournamentDtoBody);

        MvcResult result = mockMvc
                .perform(post("/tournaments").contentType("application/json").characterEncoding("UTF-8").content(body))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        TournamentDto tournamentDtoResponse = objectMapper.treeToValue(data, TournamentDto.class);

        Assert.assertEquals(tournamentDtoBody.getName(),tournamentDtoResponse.getName());

    }


}
