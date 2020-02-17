package com.fighter.demo.controllers;

import com.fighter.demo.Fighter;
import com.fighter.demo.Tournament;
import com.fighter.demo.exception.TournamentEndedException;
import com.fighter.demo.exception.TournamentNotFoundException;
import com.fighter.demo.service.FighterService;
import com.fighter.demo.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class TournamentController {

    private TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/new")
    public ResponseEntity<Tournament> startNewTournament(){
        return new ResponseEntity<>(tournamentService.newTournament(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> seeTournament(@PathVariable String id){
        return new ResponseEntity<>(tournamentService.tournamentStatus(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/fight")
    public ResponseEntity<Fighter> startFight(@PathVariable String id){
        return new ResponseEntity<>(tournamentService.fight(id), HttpStatus.OK);
    }


}
