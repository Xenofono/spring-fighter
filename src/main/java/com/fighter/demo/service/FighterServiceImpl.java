package com.fighter.demo.service;



import com.fighter.demo.models.dto.Fighter;
import com.fighter.demo.entities.FighterEntity;
import com.fighter.demo.exception.FighterNotFoundException;
import com.fighter.demo.repositories.FighterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer exists between the controller and the repository, it applies logic to the data before retrieving as well as error handling
 * @author Kristoffer Näsström
 */
@Service
public class FighterServiceImpl implements FighterService {


    private FighterRepository fighterRepository;

    public FighterServiceImpl(FighterRepository fighterRepository){
        this.fighterRepository = fighterRepository;
    }

    //Retrieves all fighters in repository
    @Override
    public List<Fighter> findAll() {
        List<FighterEntity> fighterEntities = fighterRepository.findAll();
        return fighterEntities.stream().map(this::entityToFighter)
                .collect(Collectors.toList());
    }

    //Retrieves specific fighter by id or else throws custom FighterNotFoundException which returns error
    public Fighter findById(String id){
        int convertedId = stringIdToInt(id);
        return fighterRepository.findById(convertedId)
                .map(this::entityToFighter)
                .orElseThrow(FighterNotFoundException::new);

    }

    @Override
    public void save(Fighter fighter) {
        FighterEntity entityToUpdate = fighterToEntity(fighter);
        fighterRepository.save(entityToUpdate);
    }

    //helper method that transforms FighterEntity from the database to a Fighter
    private Fighter entityToFighter(FighterEntity entity){
        Fighter fighter = new Fighter();
        BeanUtils.copyProperties(entity, fighter);
        fighter.calculateHealth();
        return fighter;
    }
    //helper method that transforms Fighter to database FighterEntity
    private FighterEntity fighterToEntity(Fighter fighter){
        FighterEntity entity = new FighterEntity();
        BeanUtils.copyProperties(fighter, entity);
        return entity;
    }

    private int stringIdToInt(String id){
        try{
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new FighterNotFoundException();
        }
    }
}
