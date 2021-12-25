package com.obodnarchuk.department;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordNotFoundException;

class DepartmentUtil {

    protected static Department findDepartmentOrThrow(long id, DepartmentRepository repository) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    protected static DepartmentResponseDTO mapToResponseDTO(Department department, ObjectMapper mapper) {
        return mapper.convertValue(department, DepartmentResponseDTO.class);
    }

    public static Department mapToEntity(DepartmentRequestDTO requestDTO,ObjectMapper mapper) {
        return mapper.convertValue(requestDTO, Department.class);
    }
}
