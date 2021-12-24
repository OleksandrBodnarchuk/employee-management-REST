package com.obodnarchuk.department;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    final ObjectMapper mapper;
    final DepartmentRepository repository;

    public DepartmentService(ObjectMapper mapper, DepartmentRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public DepartmentResponseDTO saveDepartment(DepartmentRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        return null;
    }

    @Override
    public void deleteDepartmentById(long id) {

    }

    @Override
    public DepartmentResponseDTO updateDepartment(long id, DepartmentRequestDTO requestDTO) {
        Department department;
        try {
            department = findPositionOrThrow(id);
            department.setName(requestDTO.getName());
            department.setAddress(requestDTO.getAddress());
        } catch (RecordNotFoundException e) {
            department=mapper.convertValue(requestDTO,Department.class);
        }
        repository.save(department);
        return mapToResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(long id) {
        return null;
    }

    private Department findPositionOrThrow(long id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    private DepartmentResponseDTO mapToResponseDTO(Department department) {
        return mapper.convertValue(department, DepartmentResponseDTO.class);
    }
}
