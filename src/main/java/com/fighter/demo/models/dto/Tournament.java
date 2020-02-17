package com.fighter.demo.models.dto;

import com.fighter.demo.service.FighterService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Data
@AllArgsConstructor
public class Tournament {

    private String id;
    private FighterService fighterService;
    private List<Fighter> allFighters;
    private LinkedList<Fighter> fightersRemaining;
    private Consumer<Fighter> fighterLoss = (fighter -> {
        fighter.setLosses((short) (fighter.getLosses()+1));
        fightersRemaining.remove(fighter);
        fighterService.save(fighter);
    });
    private Consumer<Fighter> fighterWin = (fighter -> {
        fighter.setWins((short) (fighter.getWins()+1));
        fighterService.save(fighter);
    });

    public Tournament(String id, FighterService fighterService) {
        this.id = id;
        this.fighterService = fighterService;
        this.allFighters = new ArrayList<>(fighterService.findAll());
        Collections.shuffle(this.allFighters);
        this.fightersRemaining = new LinkedList<>(allFighters);
    }

    public Fighter fight(){
        Fighter fighter1 = this.fightersRemaining.get(0);
        Fighter fighter2 = this.fightersRemaining.get(1);

        int hp1 = fighter1.getHealth();
        int hp2 = fighter2.getHealth();
        while(hp1 > 0 && hp2 > 0){
            hp1 -= (int) (Math.random()*10);
            hp2 -= (int) (Math.random()*10);

            if(hp1<=0){
                fighterLoss.accept(fighter1);
            }
            else if(hp2 <=0){
                fighterLoss.accept(fighter2);
            }
        }

        //Move winning fighter to back of the list
        fightersRemaining.addLast(fightersRemaining.removeFirst());
        fighterWin.accept(fightersRemaining.getLast());
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
