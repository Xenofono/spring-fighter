package com.fighter.demo.models.dto;

import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.repositories.FighterRepository;
import com.fighter.demo.service.FighterService;
import com.fighter.demo.service.FighterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("entity")
class FighterTest {

    private FighterService fighterService;
    private FighterRepository fighterRepository;

    @BeforeEach
    void setUp(){
        List<Fighter> fighters = new LinkedList<>();
        for (int i = 0; i < 8; i++) {

            Fighter fighter = new Fighter();
            fighter.setId(i);
            fighter.setName("fighter"+i);
            if(i < 2){
                fighter.setWins((short) (10*i));
                fighter.setLosses((short) (2*i));
            }
            else{
                fighter.setWins((short) (Math.random()*100));
                fighter.setLosses((short) (Math.random()*100));
            }

            fighter.calculateHealth();
            fighters.add(fighter);
        }
        fighterRepository = Mockito.mock(FighterRepository.class);
        fighterService = Mockito.mock(FighterService.class);

        Mockito.when(fighterService.findAll()).thenReturn(fighters);
        Mockito.when(fighterService.findById("1")).thenReturn(fighters.get(1));
        Mockito.when(fighterService.findById("12345")).thenReturn(null);

    }

    @Test
    void getAllFighters() {
        assertEquals(8, fighterService.findAll().size());
    }

    @Test
    void getFighterById() {
        assertNotNull(fighterService.findById("1"));
        assertNull(fighterService.findById("12345"));
    }
}