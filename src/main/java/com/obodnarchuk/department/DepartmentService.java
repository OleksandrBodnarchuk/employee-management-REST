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

        return DepartmentUtil.mapToResponseDTO(department,mapper);
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        List<Department> departmentsFromDB = repository.findAll();
        return departmentsFromDB.stream().map(e->DepartmentUtil.mapToResponseDTO(e,mapper)).collect(Collectors.toList());    }

    @Override
    public void deleteDepartmentById(long id) {
        Department department = DepartmentUtil.findDepartmentOrThrow(id,repository);
        repository.delete(department);
    }

    @Override // TODO: throws error
    public DepartmentResponseDTO updateDepartment(long id, DepartmentRequestDTO requestDTO) {
        Department department;
        try {
            department = DepartmentUtil.findDepartmentOrThrow(id,repository);
            department.setName(requestDTO.getName());
            if (requestDTO.getAddress()!=null){
                department.setAddress(requestDTO.getAddress());
            }
        } catch (RecordNotFoundException e) {
            department= DepartmentUtil.mapToEntity(requestDTO,mapper);
        }
        repository.save(department);
        return DepartmentUtil.mapToResponseDTO(department,mapper);
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(long id) {
        Department department = DepartmentUtil.findDepartmentOrThrow(id,repository);
        return DepartmentUtil.mapToResponseDTO(department,mapper);
    }

    @Override
    public Department getDepartmentByName(String name) {
        return repository.findDepartmentByTitle(name);
    }
}
