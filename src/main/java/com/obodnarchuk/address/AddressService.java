package com.obodnarchuk.address;

import com.obodnarchuk.department.DepartmentRequestDTO;
import com.obodnarchuk.department.DepartmentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService{
    @Override
    public AddressResponseDTO saveAddress(AddressRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteAddressById(long id) {

    }

    @Override
    public AddressResponseDTO updatePosition(long id, AddressRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        return null;
    }

    @Override
    public DepartmentResponseDTO updateAddress(long id, DepartmentRequestDTO requestDTO) {
        return null;
    }
}
