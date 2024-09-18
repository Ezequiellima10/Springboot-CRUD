package com.example.java.repository;

import com.example.java.model.Gardener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GardenerRepository extends JpaRepository<Gardener, Long> {

}
