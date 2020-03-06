package com.fighter.demo.models.dto;

import com.fighter.demo.entities.FighterEntity;
import com.fighter.demo.repositories.FighterRepository;
import com.fighter.demo.service.FighterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class TournamentTest {

    private Tournament tournament;


    private FighterService fighterService;
    private FighterRepository fighterRepository;

    @BeforeEach
    void setUp(){
        List<Fighter> fighters = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            Fighter fighter = new Fighter();
            fighter.setId(i);
            fighter.setName("fighter"+i);
            fighter.setHealth((short) 100);
            fighter.setWins((short) 0);
            fighter.setLosses((short) 0);
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
}