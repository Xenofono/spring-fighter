package com.fighter.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fighter.demo.entities.TournamentEntity;
import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.models.dto.FighterMatch;
import com.fighter.demo.models.dto.Tournament;
import com.fighter.demo.models.response.TournamentResponse;
import com.fighter.demo.service.FighterService;
import com.fighter.demo.service.FighterServiceImpl;
import com.fighter.demo.service.TournamentService;
import com.fighter.demo.service.TournamentServiceImpl;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TournamentControllerTest {


    @Autowired
    private MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    String currentMatchId;

    @BeforeEach
    public void setup() throws Exception {
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/new"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn();

        currentMatchId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    @Test
    void startNewTournament() throws Exception {
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/new"))
                .andDo(print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
        .andReturn();

        final TournamentResponse tournament = mapper.readValue(result.getResponse().getContentAsByteArray(), TournamentResponse.class);
        assertEquals(tournament.getId().length(), 4);
        assertEquals(tournament.getFightersRemaining().size(), 8);

    }

    @Test
    void seeTournament() throws Exception {

    }

    @Test
    void getOldTournament() throws Exception {
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/record/"+"futa"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn();

        final TournamentEntity tournament = mapper.readValue(result.getResponse().getContentAsByteArray(), TournamentEntity.class);
        assertEquals(tournament.getId(), "futa");
        assertEquals(tournament.getWinnerId(), 8);
    }

    @Test
    void getNextFight() throws Exception {
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/"+currentMatchId+"/upcoming"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fighter1").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fighter2").exists())
                .andReturn();

        final FighterMatch match = mapper.readValue(result.getResponse().getContentAsByteArray(), FighterMatch.class);
        assertNotNull(match.getFighter1());
        assertNotNull(match.getFighter2());
    }

    @Test
    void startFight() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/"+currentMatchId+"/fight"))
                .andDo(print())
                .andReturn();

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/"+currentMatchId+"/fight"))
                .andDo(print())
                .andReturn();

        final FighterMatch match = mapper.readValue(result.getResponse().getContentAsByteArray(), FighterMatch.class);
        assertNotNull(match.getFighter1());
        assertNotNull(match.getFighter2());
        assertNotNull(match.getFightLog());

        MvcResult tournamentAfter2Fights = this.mvc.perform(MockMvcRequestBuilders.get("/"+currentMatchId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn();

        final TournamentResponse tournament = mapper.readValue(tournamentAfter2Fights.getResponse().getContentAsByteArray(), TournamentResponse.class);
        assertEquals(tournament.getId(), currentMatchId);
        assertEquals(tournament.getFightersRemaining().size(), 6);
    }
}