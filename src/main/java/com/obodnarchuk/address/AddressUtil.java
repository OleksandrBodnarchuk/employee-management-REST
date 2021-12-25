package com.obodnarchuk.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordNotFoundException;

public class AddressUtil {
    protected static Address findAddressByIdOrThrow(long id, AddressRepository addressRepository) {
        return addressRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    protected static AddressResponseDTO mapToResponseDTO(Address address, ObjectMapper mapper) {
        return mapper.convertValue(address, AddressResponseDTO.class);
    }

    protected static void checkNewAddressOrThrow(AddressRequestDTO requestDTO, AddressRepository addressRepository) {
        addressRepository.checkForAddress(requestDTO.getCity(), requestDTO.getStreet(), requestDTO.getHouseNr())
                .orElseThrow(() -> new RecordNotFoundException(requestDTO.toString()));
    }

    public static Address mapRequestToAddress(AddressRequestDTO requestDTO, ObjectMapper mapper) {
        return mapper.convertValue(requestDTO, Address.class);
    }

    protected static void checkDTOValuesAndMap(AddressRequestDTO requestDTO, Address address) {
        if (requestDTO.getCity() != null) {
            address.setCity(requestDTO.getCity());
        }

        if (requestDTO.getHouseNr() != null) {
            address.setHouseNr(requestDTO.getHouseNr());
        }

        if (requestDTO.getStreet() != null) {
            address.setStreet(requestDTO.getStreet());
        }

        if (requestDTO.getZipCode() != null) {
            address.setZipCode(requestDTO.getZipCode());
        }
    }

    public static AddressResponseDTO mapToResponse(Address address, ObjectMapper mapper) {
        return mapper.convertValue(address,AddressResponseDTO.class);
    }
}
