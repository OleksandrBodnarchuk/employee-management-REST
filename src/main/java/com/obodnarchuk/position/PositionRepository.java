package com.obodnarchuk.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
    @Query("SELECT p FROM Position p WHERE p.title = :title")
    Position findPositionByTitle(@Param("title")String title);

}
