package com.fighter.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Database entity representation of a tournament
 * @author Kristoffer Näsström
 */
@Entity
@Table(name = "Tournaments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentEntity {

    @Id
    private String id;
    @Column(name = "winner_id")
    private int winnerId;
}
