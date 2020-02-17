package com.fighter.demo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Tournament {

    private String id;
    private List<Fighter> allFighters;
    private List<Fighter> fightersRemaining;

    public Tournament(String id, List<Fighter> fightersInTournament) {
        this.id = id;
        this.allFighters = new ArrayList<>(fightersInTournament);
        Collections.shuffle(this.allFighters);
        this.fightersRemaining = new ArrayList<>(allFighters);
    }

    public Fighter fight(){
        Fighter fighter1 = this.fightersRemaining.get(0);
        Fighter fighter2 = this.fightersRemaining.get(1);

        Fighter winner;
        if(Math.random()<0.5){
            fightersRemaining.remove(fighter2);
            winner = fighter1;
        }
        else{
            fightersRemaining.remove(fighter1);
            winner = fighter2;
        }
        fightersRemaining.set(fightersRemaining.size()-1, winner);

        if(fightersRemaining.size() == 1){
            tournamentOver();
            return null;
        }
        return winner;
    }

    private void tournamentOver(){
        System.out.println("TOURNAMENT OVER, WINNER IS: " + fightersRemaining.get(0));
    }

    public static String randomId(){
        StringBuilder id = new StringBuilder();

        for (int i = 0; i <= 3; i++) {
            id.append((char) (Math.random()*26 + 'a'));
        }
        System.out.println(id.toString());
        return id.toString();
    }
}
