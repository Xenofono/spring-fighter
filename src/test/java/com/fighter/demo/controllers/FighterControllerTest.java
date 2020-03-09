package com.fighter.demo.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.repositories.FighterRepository;
import com.fighter.demo.service.FighterService;
import com.fighter.demo.service.FighterServiceImpl;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("controllers")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FighterControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();


    @Test
    void getAllFighters() throws Exception {
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/fighters/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Fighter> fightersReceived = mapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<>() {
        });
        assertEquals(8, fightersReceived.size());
    }

    @Test
    void getFighterById() throws Exception {
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/fighters/"+2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Fighter fighterReceived = mapper.readValue(result.getResponse().getContentAsByteArray(), Fighter.class);
        assertNotNull(fighterReceived.getName());

        this.mvc.perform(MockMvcRequestBuilders.get("/fighters/"+1234))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}