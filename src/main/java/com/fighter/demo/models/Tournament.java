package com.fighter.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class Tournament {

    private String id;
    private List<Fighter> allFighters;
    private LinkedList<Fighter> fightersRemaining;
    private Fighter winner;

    public Tournament(String id, List<Fighter> fightersInTournament) {
        this.id = id;
        this.allFighters = new ArrayList<>(fightersInTournament);
        Collections.shuffle(this.allFighters);
        this.fightersRemaining = new LinkedList<>(allFighters);
    }

    public Fighter fight(){
        Fighter fighter1 = this.fightersRemaining.get(0);
        Fighter fighter2 = this.fightersRemaining.get(1);

        if(Math.random()<0.5){
            fightersRemaining.remove(fighter2);
        }
        else{
            fightersRemaining.remove(fighter1);
        }

        if(fightersRemaining.size() == 1) tournamentOver();

        return fightersRemaining.getLast();
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
