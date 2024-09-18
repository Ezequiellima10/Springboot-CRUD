package com.example.java.repository;

import com.example.java.model.Gardener;
import com.example.java.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

    Optional<Plant> findBySpecies(String species);

    @Query("SELECT p FROM Plant p ORDER BY LOWER(p.species)ASC")
    List<Plant> findAllByOrderBySpeciesIgnoreCaseAsc();

    void deleteByGardener(Gardener gardener);
}
