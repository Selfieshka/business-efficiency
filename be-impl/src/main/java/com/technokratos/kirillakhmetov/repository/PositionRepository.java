package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByName(String name);

    List<String> getPositionsByName(String name);

    List<String> findByNameIn(Collection<String> names);
}