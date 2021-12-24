package com.obodnarchuk.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("SELECT d FROM Department d WHERE d.name = :name")
    Department findDepartmentByTitle(@Param("name") String name);
}
