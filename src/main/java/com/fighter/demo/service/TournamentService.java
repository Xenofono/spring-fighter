package com.fighter.demo.service;

import com.fighter.demo.Fighter;
import com.fighter.demo.Tournament;

public interface TournamentService {

    Tournament newTournament();
    Tournament tournamentStatus(String id);
    Fighter fight(String id);
}
