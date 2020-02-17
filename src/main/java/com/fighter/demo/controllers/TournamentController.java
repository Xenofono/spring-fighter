package com.fighter.demo.controllers;

import com.fighter.demo.entities.TournamentEntity;
import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.models.dto.Tournament;
import com.fighter.demo.models.dto.TournamentConverter;
import com.fighter.demo.models.response.TournamentResponse;
import com.fighter.demo.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TournamentController {

    private TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/new")
    public ResponseEntity<TournamentResponse> startNewTournament(){
        TournamentResponse response = TournamentConverter.tournamentToResponse(tournamentService.newTournament());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentResponse> seeTournament(@PathVariable String id){
        TournamentResponse response = TournamentConverter.tournamentToResponse(tournamentService.tournamentStatus(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<TournamentEntity> getOldTournament(@PathVariable String id){
        return new ResponseEntity<>(tournamentService.getOldTournament(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/fight")
    public ResponseEntity<Fighter> startFight(@PathVariable String id){
        return new ResponseEntity<>(tournamentService.fight(id), HttpStatus.OK);
    }


}
