package com.fighter.demo.controllers;

import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.service.FighterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class contains routes for controlling fighters, currently not used in live environment
 * @author Kristoffer Näsström
 */
@RestController
public class FighterController {

    private FighterService fighterService;

    public FighterController(FighterService fighterService) {
        this.fighterService = fighterService;
    }

    @GetMapping("fighters/")
    public List<Fighter> getAllFighters(){
        return fighterService.findAll();
    }

    @GetMapping("fighters/{id}")
    public Fighter getFighterById(@PathVariable("id") String id){
        return fighterService.findById(id);
    }
}
