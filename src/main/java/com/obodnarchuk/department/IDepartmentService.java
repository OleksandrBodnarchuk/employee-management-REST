package com.obodnarchuk.department;

import java.util.List;

public interface IDepartmentService {
    DepartmentResponseDTO saveDepartment(DepartmentRequestDTO requestDTO);

    List<DepartmentResponseDTO> getAllDepartments();

    void deleteDepartmentById(long id);

    DepartmentResponseDTO updateDepartment(long id, DepartmentRequestDTO requestDTO);

    DepartmentResponseDTO getDepartmentById(long id);
}
