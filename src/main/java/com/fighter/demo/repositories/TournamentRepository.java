package com.fighter.demo.repositories;

import com.fighter.demo.entities.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<TournamentEntity, String> {
}
