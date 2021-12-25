package com.obodnarchuk.department;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Department department = repository.findDepartmentByTitle(requestDTO.getName());
        if (department != null) {
            throw new RecordExistsException(department.getId());
        } else {
            department = new Department(requestDTO.getName());
            if (requestDTO.getAddress()!=null){
                department.setAddress(requestDTO.getAddress());
            }
            repository.save(department);
        }

        return mapToResponseDTO(department);
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        List<Department> departmentsFromDB = repository.findAll();
        return departmentsFromDB.stream().map(this::mapToResponseDTO).collect(Collectors.toList());    }

    @Override
    public void deleteDepartmentById(long id) {
        Department department = findDepartmentOrThrow(id);
        repository.delete(department);
    }

    @Override
    public DepartmentResponseDTO updateDepartment(long id, DepartmentRequestDTO requestDTO) {
        Department department;
        try {
            department = findDepartmentOrThrow(id);
            department.setName(requestDTO.getName());
            if (requestDTO.getAddress()!=null){
                department.setAddress(requestDTO.getAddress());
            }
        } catch (RecordNotFoundException e) {
            department=mapper.convertValue(requestDTO,Department.class);
        }
        repository.save(department);
        return mapToResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(long id) {
        return mapToResponseDTO(findDepartmentOrThrow(id));
    }

    private Department findDepartmentOrThrow(long id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    private DepartmentResponseDTO mapToResponseDTO(Department department) {
        return mapper.convertValue(department, DepartmentResponseDTO.class);
    }
}
