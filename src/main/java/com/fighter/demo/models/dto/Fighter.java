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
    private byte health;
    private short wins;
    private short losses;


    public void calculateHealth(){
        this.health = (byte) (100+wins+losses);
    }

}
