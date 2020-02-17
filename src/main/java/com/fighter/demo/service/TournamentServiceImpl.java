package com.fighter.demo.service;

import com.fighter.demo.Tournament;
import com.fighter.demo.entities.TournamentEntity;
import com.fighter.demo.repositories.TournamentRepository;

public class TournamentServiceImpl implements TournamentService {

    private TournamentRepository tournamentRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public void saveTournament(Tournament tournament) {
        TournamentEntity entity = new TournamentEntity();
        entity.setId(tournament.getId());
        entity.setWinnerId(tournament.getFightersRemaining().get(0).getId());
        tournamentRepository.save(entity);
    }
}
