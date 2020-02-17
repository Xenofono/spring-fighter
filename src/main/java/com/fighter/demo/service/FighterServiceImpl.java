package com.fighter.demo.service;



import com.fighter.demo.Fighter;
import com.fighter.demo.entities.FighterEntity;
import com.fighter.demo.exception.FighterNotFoundException;
import com.fighter.demo.repositories.FighterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FighterServiceImpl implements FighterService {


    FighterRepository fighterRepository;

    public FighterServiceImpl(FighterRepository fighterRepository){
        this.fighterRepository = fighterRepository;
    }

    @Override
    public List<Fighter> findAll() {
        List<FighterEntity> fighterEntities = fighterRepository.findAll();
        return fighterEntities.stream().map(this::entityToFighter)
                .collect(Collectors.toList());
    }

    public Fighter findById(String id){
        int convertedId = stringIdToInt(id);
        return fighterRepository.findById(convertedId)
                .map(this::entityToFighter)
                .orElseThrow(() -> new FighterNotFoundException("Hittade inte den fightern"));

    }

    private Fighter entityToFighter(FighterEntity entity){
        Fighter fighter = new Fighter();
        BeanUtils.copyProperties(entity, fighter);
        fighter.calculateHealth();
        return fighter;
    }

    private int stringIdToInt(String id){
        try{
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new FighterNotFoundException("Felaktigt idformat, bara nummer godtas");
        }
    }
}
