package com.probasteReiniciando.TPTACS.controllers.result;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.Result;
import com.probasteReiniciando.TPTACS.dto.ResultDto;
import com.probasteReiniciando.TPTACS.services.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private final ModelMapperTacs mapper = new ModelMapperTacs();

    @Test
    public void createResult() throws Exception {

        Result expectedResult = Result.builder().username("pepe").points(2).build();

        when(userService.createResult("pepe", expectedResult)).thenReturn(expectedResult);

        String body = objectMapper.writeValueAsString(mapper.map(expectedResult, ResultDto.class));

        MvcResult result = mockMvc
                .perform(
                        post("/results")
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
        ResultDto actualResult = objectMapper.treeToValue(data, ResultDto.class);

        Assertions.assertEquals(expectedResult, mapper.map(actualResult, Result.class));

    }

    @Test
    public void modifyResult() throws Exception {

        Result expectedResult = Result.builder().username("pepe").points(2).build();

        when(userService.modifyResult("pepe", expectedResult, 1)).thenReturn(expectedResult);

        String body = objectMapper.writeValueAsString(mapper.map(expectedResult, ResultDto.class));

        MvcResult result = mockMvc
                .perform(
                        put("/results/1")
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
        ResultDto actualResult = objectMapper.treeToValue(data, ResultDto.class);

        Assertions.assertEquals(expectedResult, mapper.map(actualResult, Result.class));

    }

    @Test
    public void getResults() throws Exception {

        Result expectedResult1 = Result.builder().username("pepe").points(2).build();
        Result expectedResult2 = Result.builder().username("pepe").points(3).build();
        List<Result> expectedrResults = List.of(expectedResult1,expectedResult2);

        when(userService.getTodayResultsByUser("pepe")).thenReturn(expectedrResults);

        MvcResult result = mockMvc
                .perform(
                        get("/results/user")
                                .contentType("application/json")
                                .characterEncoding("UTF-8")
                                .requestAttr("userAttributeName","pepe")
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode data = jsonNode.get("response");
        List<ResultDto> actualResults = new ArrayList<>(Arrays.asList(objectMapper.treeToValue(data, ResultDto[].class)));

        Assertions.assertEquals(expectedrResults, mapper.mapList(actualResults, Result.class));

    }

}
