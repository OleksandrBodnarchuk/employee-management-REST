package com.obodnarchuk.address;

import com.obodnarchuk.department.DepartmentRequestDTO;
import com.obodnarchuk.department.DepartmentResponseDTO;

import java.util.List;


public interface IAddressService {
    AddressResponseDTO saveAddress(AddressRequestDTO requestDTO);

    void deleteAddressById(long id);

    AddressResponseDTO updatePosition(long id, AddressRequestDTO requestDTO);

    List<AddressResponseDTO> getAllAddresses();

    DepartmentResponseDTO updateAddress(long id, DepartmentRequestDTO requestDTO);
}
