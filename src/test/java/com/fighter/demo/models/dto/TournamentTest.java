package com.fighter.demo.models.dto;

import com.fighter.demo.entities.FighterEntity;
import com.fighter.demo.repositories.FighterRepository;
import com.fighter.demo.service.FighterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("entity")
class TournamentTest {

    private Tournament tournament;


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

        fighterService = Mockito.mock(FighterService.class);
        fighterRepository = Mockito.mock(FighterRepository.class);
        Mockito.when(fighterService.findAll()).thenReturn(fighters);

        FighterEntity entity = new FighterEntity();
        Mockito.when(fighterRepository.save(Mockito.any(FighterEntity.class))).thenReturn(entity);
//        Mockito.doNothing().when(fighterService).save(Mockito.any(Fighter.class));
        tournament = new Tournament();
        tournament.setFighterService(fighterService);
        tournament.setAllFighters(fighterService.findAll());
        tournament.setFightersRemaining((LinkedList<Fighter>) fighterService.findAll());
        tournament.setFighterMatches(new ArrayList<>());
        tournament.setupMatches();
    }

    @Test
    void fight() {
    }

    @Test
    void getFightersRemaining() {
        assertEquals(8, tournament.getFightersRemaining().size());
        tournament.fight();
        tournament.fight();
        tournament.fight();
        assertEquals(5, tournament.getFightersRemaining().size());
    }

    @Test
    void getNextMatch() {

        Assertions.assertAll(
                () ->Assertions.assertSame(tournament.getNextMatch().getFighter1(), tournament.getFightersRemaining().getFirst()),
                () -> Assertions.assertSame(tournament.getNextMatch().getFighter2(), tournament.getFightersRemaining().get(1))
        );
        FighterMatch match = tournament.fight();
        Fighter winner = match.getWinner();

        Assertions.assertAll(
                () -> assertSame(winner, tournament.getFightersRemaining().getLast()),
                () ->Assertions.assertSame(tournament.getNextMatch().getFighter1(), tournament.getFightersRemaining().getFirst()),
                () -> Assertions.assertSame(tournament.getNextMatch().getFighter2(), tournament.getFightersRemaining().get(1))
        );
        assertSame(winner, tournament.getFightersRemaining().getLast());


    }

    @Test
    void fightOddsTest(){
        System.out.println(tournament.getNextMatch().getFighter1());
        System.out.println(tournament.getNextMatch().getFighter2());
        System.out.println(tournament.getNextMatch().getFighter1().getOdds());
        Assertions.assertAll(
                () -> assertEquals(2.08, tournament.getNextMatch().getFighter1().getOdds()),
                () -> assertEquals(1.93, tournament.getNextMatch().getFighter2().getOdds())
        );
    }
}