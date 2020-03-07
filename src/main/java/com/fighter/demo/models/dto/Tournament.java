package com.fighter.demo.models.dto;

import com.fighter.demo.service.FighterService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    private String id;
    private FighterService fighterService;
    private List<Fighter> allFighters;
    private LinkedList<Fighter> fightersRemaining;
    private List<FighterMatch> fighterMatches;
    private FighterMatch nextMatch;
    private Consumer<Fighter> fighterLoss = (fighter -> {
        fighter.setLosses((short) (fighter.getLosses() + 1));
        fightersRemaining.remove(fighter);
        fighterService.save(fighter);
    });
    private Consumer<Fighter> fighterWin = (fighter -> {
        fighter.setWins((short) (fighter.getWins() + 1));
        fighterService.save(fighter);
    });

    public Tournament(String id, FighterService fighterService) {
        this.id = id;
        this.fighterService = fighterService;
        this.allFighters = new ArrayList<>(fighterService.findAll());
        Collections.shuffle(this.allFighters);
        this.fightersRemaining = new LinkedList<>(allFighters);
        this.fighterMatches = new ArrayList<>();
        this.setupMatches();
    }

    /*
    loops over all remaining fighters and assigns them matches, when all the fights are over and the losers are removed
    the method is run again for the remaining fighters
     */
    public void setupMatches() {
        for (int i = 0; i < fightersRemaining.size() - 1; i = i + 2) {
            FighterMatch fighterMatch = new FighterMatch();
            fighterMatch.setFighter1(fightersRemaining.get(i));
            fighterMatch.setFighter2(fightersRemaining.get(i + 1));
            fighterMatch.setFightLog(new ArrayList<>());
            this.setOdds(fighterMatch);
            fighterMatches.add(fighterMatch);
        }
        this.nextMatch = fighterMatches.get(0);
    }
    /*
            calculates odds by dividing fighters healths with each other and adding 1 double
            double is then passed to roundDouble which rounds it to 2 decimals.
            The number of zeroes in the other value determines how many decimals we want, 100f = 2 zeroes = 2 decimals

            This method is much faster than using a formatter
             */
    private void setOdds(FighterMatch match) {

        BiFunction<Fighter, Fighter, Double> calculateOdds = (thisFighter, otherFighter) ->
                (double) otherFighter.getHealth() / thisFighter.getHealth() + 1d;

        Function<Double, Double> roundDouble = (baseOdds) -> Math.round((baseOdds * 100d)) / 100d;

        double f1Odds = calculateOdds.andThen(roundDouble).apply(match.getFighter1(), match.getFighter2());
        double f2Odds = calculateOdds.andThen(roundDouble).apply(match.getFighter2(), match.getFighter1());

        match.getFighter1().setOdds(f1Odds);
        match.getFighter2().setOdds(f2Odds);
    }


    public FighterMatch fight() {
        FighterMatch match = this.fighterMatches.get(0);
        Fighter fighter1 = match.getFighter1();
        Fighter fighter2 = match.getFighter2();

        int hp1 = fighter1.getHealth();
        int hp2 = fighter2.getHealth();
        while (hp1 > 0 && hp2 > 0) {
            final int attackDamage = (int) (Math.random() * 10);
            match.getFightLog().add(fighter1.getName() + " blev slagen för " + attackDamage);
            hp1 -= attackDamage;

            final int attackDamage2 = (int) (Math.random() * 10);
            match.getFightLog().add(fighter2.getName() + " blev slagen för " + attackDamage2);
            hp2 -= (int) (Math.random() * 10);

            if (hp1 <= 0) {
                fighterLoss.accept(fighter1);
                match.setWinner(fighter2);
            } else if (hp2 <= 0) {
                fighterLoss.accept(fighter2);
                match.setWinner(fighter1);

            }
        }

        //Move winning fighter to back of the list
        fightersRemaining.addLast(fightersRemaining.removeFirst());
        fighterWin.accept(fightersRemaining.getLast());
        FighterMatch matchToReturn = fighterMatches.remove(0);
        if (fighterMatches.isEmpty()) {
            if (fightersRemaining.size() == 1) {
                tournamentOver();
            } else {
                this.setupMatches();
            }
        } else {
            this.nextMatch = fighterMatches.get(0);
        }

        return matchToReturn;
    }

    private void tournamentOver() {
        System.out.println("TOURNAMENT OVER, WINNER IS: " + fightersRemaining.get(0));
    }

    public static String randomId() {
        StringBuilder id = new StringBuilder();

        for (int i = 0; i <= 3; i++) {
            id.append((char) (Math.random() * 26 + 'a'));
        }
        System.out.println(id.toString());
        return id.toString();
    }
}
