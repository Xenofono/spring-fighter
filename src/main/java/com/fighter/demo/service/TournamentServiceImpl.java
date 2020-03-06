package com.fighter.demo.service;

import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.models.dto.FighterMatch;
import com.fighter.demo.models.dto.Tournament;
import com.fighter.demo.entities.TournamentEntity;
import com.fighter.demo.exception.TournamentNotFoundException;
import com.fighter.demo.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TournamentServiceImpl implements TournamentService {


    private Map<String, Tournament> tournamentCache = new ConcurrentHashMap<>();
    private TournamentRepository tournamentRepository;
    private FighterService fighterService;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, FighterService fighterService) {
        this.tournamentRepository = tournamentRepository;
        this.fighterService = fighterService;
    }

    @Override
    public Tournament newTournament() {
        String newId = Tournament.randomId();
        while(tournamentCache.containsKey(newId) || tournamentRepository.findById(newId).isPresent()){
            newId = Tournament.randomId();
        }
        Tournament newTournament = new Tournament(newId, fighterService);
        tournamentCache.put(newId, newTournament);
        return newTournament;
    }

    @Override
    public Tournament tournamentStatus(String id) {
        return Optional.ofNullable(tournamentCache.get(id))
                .orElseThrow(TournamentNotFoundException::new);
    }

    @Override
    public TournamentEntity getOldTournament(String id) {
        return tournamentRepository.findById(id)
                .orElseThrow(TournamentNotFoundException::new);
    }

    @Override
    public FighterMatch fight(String id) {
        Tournament tournament = Optional.ofNullable(tournamentCache.get(id))
                .orElseThrow(TournamentNotFoundException::new);

        if(tournament.getFightersRemaining().size() > 2){
            return tournament.fight();
        }
        else{
            FighterMatch winner = tournament.fight();
            saveTournament(tournament);
            tournamentCache.remove(id);
            System.out.println(winner);
            return winner;
        }
    }

    @Override
    public FighterMatch getNextMatch(String id) {
        Tournament tournament = Optional.ofNullable(tournamentCache.get(id))
                .orElseThrow(TournamentNotFoundException::new);

        return tournament.getNextMatch();
    }

    private void saveTournament(Tournament tournament) {
        System.out.println("Saving");
        TournamentEntity entity = new TournamentEntity();
        entity.setId(tournament.getId());
        entity.setWinnerId(tournament.getFightersRemaining().get(0).getId());
        tournamentRepository.save(entity);
    }
}
