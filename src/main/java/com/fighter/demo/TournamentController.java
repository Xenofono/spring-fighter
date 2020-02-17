package com.fighter.demo;

import com.fighter.demo.exception.TournamentEndedException;
import com.fighter.demo.exception.TournamentNotFoundException;
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

    Map<String, Tournament> tournamentCache = new HashMap<>();
    FighterService fighterService;

    public TournamentController(FighterService fighterService) {
        this.fighterService = fighterService;
    }



    @GetMapping("/new")
    public Tournament startNewTournament(){
        String newId = Tournament.randomId();
        while(tournamentCache.containsKey(newId)){
            newId = Tournament.randomId();
        }
        Tournament newTournament = new Tournament(newId, fighterService.findAll());
        tournamentCache.put(newId, newTournament);
        return newTournament;
    }

    @GetMapping("/{id}")
    public Tournament seeTournament(@PathVariable String id){
        Tournament tournament = Optional.ofNullable(tournamentCache.get(id))
                .orElseThrow(() -> new TournamentNotFoundException("Ingen sån turnering hittades"));

        return tournament;
    }

    @GetMapping("/{id}/fight")
    public ResponseEntity<Fighter> startFight(@PathVariable String id){
        Tournament tournament = Optional.ofNullable(tournamentCache.get(id))
                .orElseThrow(() -> new TournamentNotFoundException("Ingen sån turnering hittades"));

        if(tournament.getFightersRemaining().size() > 1){
            return new ResponseEntity<>(tournament.fight(), HttpStatus.OK);
        }

        throw new TournamentEndedException("Turneringen har avslutats");
    }


}
