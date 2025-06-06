package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findAllByNameIn(Collection<String> names);
}