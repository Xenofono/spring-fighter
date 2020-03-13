package com.fighter.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Database entity representation of a fighter
 * @author Kristoffer Näsström
 */
@Entity
@Table(name = "Fighters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FighterEntity {
    @Id
    private int id;
    private String name;
    private short wins;
    private short losses;

    public FighterEntity(int i, String name, int wins, int losses) {
    }
}
