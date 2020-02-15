package com.fighter.demo.repositories;

import com.fighter.demo.entities.FighterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FighterRepository extends JpaRepository<FighterEntity, Integer> {
}
