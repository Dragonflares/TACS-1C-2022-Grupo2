package com.probasteReiniciando.TPTACS.controllers.result;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.services.helper.HelperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HelperService helperService;

    private final ModelMapperTacs mapper = new ModelMapperTacs();

    //TODO
    @Test
    public void createResult(){}

    //TODO
    @Test
    public void modifyResult(){}

    //TODO
    @Test
    public void getResults(){}

}
