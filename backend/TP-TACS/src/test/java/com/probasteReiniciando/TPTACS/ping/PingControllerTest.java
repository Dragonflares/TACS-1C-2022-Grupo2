package com.probasteReiniciando.TPTACS.ping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.probasteReiniciando.TPTACS.controllers.PingController;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters  = false)

public class PingControllerTest {


    @Autowired
    private MockMvc mockMvc; // injected with @AutoConfigureMockMvc

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ping() throws Exception {
        this.mockMvc
                .perform(get("/ping").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("HelloWorld"))); // we check that the Body of the answer contains our expectation
    }

}
