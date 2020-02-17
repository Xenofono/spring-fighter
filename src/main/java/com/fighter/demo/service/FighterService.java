package com.fighter.demo.service;

import com.fighter.demo.models.dto.Fighter;

import java.util.List;


public interface FighterService {

    List<Fighter> findAll();
    Fighter findById(String id);
    void save(Fighter fighter);
}
