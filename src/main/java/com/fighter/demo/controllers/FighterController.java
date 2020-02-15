package com.fighter.demo.controllers;

import com.fighter.demo.Fighter;
import com.fighter.demo.FighterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FighterController {

    FighterService fighterService;

    public FighterController(FighterService fighterService) {
        this.fighterService = fighterService;
    }

    @GetMapping("/")
    public List<Fighter> getAllFighters(){
        return fighterService.findAll();
    }

    @GetMapping("/{id}")
    public Fighter getFighterById(@PathVariable("id") String id){
        return fighterService.findById(id);
    }
}
