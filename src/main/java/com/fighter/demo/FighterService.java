package com.fighter.demo;

import org.springframework.stereotype.Service;

import java.util.List;


public interface FighterService {

    List<Fighter> findAll();
    Fighter findById(String id);
}
