package com.obodnarchuk.employee;

import com.obodnarchuk.address.Address;
import com.obodnarchuk.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.department=:dep  WHERE e.id = :empId")
    void updateEmployeeDepartment(@Param("empId") long employeeId, @Param("dep") Department newDepartment);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.address=:address  WHERE e.id = :empId")
    void updateEmployeeAddress(@Param("empId") long employeeId, @Param("address") Address address);
}
