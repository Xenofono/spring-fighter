package com.fighter.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FighterMatch {

    private Fighter fighter1;
    private Fighter fighter2;
    private Fighter winner;
    private List<String> fightLog;
}
