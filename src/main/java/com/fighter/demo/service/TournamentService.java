package com.fighter.demo.service;

import com.fighter.demo.entities.TournamentEntity;
import com.fighter.demo.models.Fighter;
import com.fighter.demo.models.Tournament;

public interface TournamentService {

    Tournament newTournament();
    Tournament tournamentStatus(String id);
    TournamentEntity getOldTournament(String id);
    Fighter fight(String id);
}
