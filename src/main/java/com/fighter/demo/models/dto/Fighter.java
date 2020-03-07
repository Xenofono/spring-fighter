package com.fighter.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fighter {

    private int id;
    private String name;
    private short health;
    private short wins;
    private short losses;
    private double odds;


    public void calculateHealth(){
        this.health = (short) (100+wins-losses);
    }

}
