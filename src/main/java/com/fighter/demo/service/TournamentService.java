package com.fighter.demo.service;

import com.fighter.demo.entities.TournamentEntity;
import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.models.dto.FighterMatch;
import com.fighter.demo.models.dto.Tournament;

public interface TournamentService {

    Tournament newTournament();
    Tournament tournamentStatus(String id);
    TournamentEntity getOldTournament(String id);
    FighterMatch fight(String id);

    FighterMatch getNextMatch(String id);
}
