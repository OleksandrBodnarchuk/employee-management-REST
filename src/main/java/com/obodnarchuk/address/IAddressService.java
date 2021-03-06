package com.obodnarchuk.address;

import java.util.List;
import java.util.Optional;


public interface IAddressService {
    AddressResponseDTO saveAddress(AddressRequestDTO requestDTO);

    void deleteAddressById(long id);

    AddressResponseDTO updateAddress(long id, AddressRequestDTO requestDTO);

    List<AddressResponseDTO> getAllAddresses();

    AddressResponseDTO getAddressById(long id);


    Optional<Address> findAddress(Address address);
}
