package com.obodnarchuk.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.department.Department;
import com.obodnarchuk.department.DepartmentResponseDTO;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService implements IAddressService {

    final AddressRepository addressRepository;
    final ObjectMapper mapper;

    public AddressService(AddressRepository addressRepository, ObjectMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public AddressResponseDTO saveAddress(AddressRequestDTO requestDTO) {
        AddressResponseDTO responseDTO;
        try {
            checkNewAddressOrThrow(requestDTO);
            throw new RecordExistsException(requestDTO.toString());
        } catch (RecordNotFoundException e) {
            Address address = mapper.convertValue(requestDTO, Address.class);
            addressRepository.save(address);
            responseDTO = mapToResponseDTO(address);
        }
        return responseDTO;
    }

    @Override
    public void deleteAddressById(long id) {
        Address address = findAddressByIdOrThrow(id);
        addressRepository.delete(address);
    }

    @Override
    public AddressResponseDTO updateAddress(long id, AddressRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        List<Address> addressesFromDB = addressRepository.findAll();
        return addressesFromDB.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public AddressResponseDTO getAddressById(long id) {
        Address address = findAddressByIdOrThrow(id);
        return mapToResponseDTO(address);
    }


    private Address findAddressByIdOrThrow(long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    private AddressResponseDTO mapToResponseDTO(Address address) {
        return mapper.convertValue(address, AddressResponseDTO.class);
    }

    private void checkNewAddressOrThrow(AddressRequestDTO requestDTO) {
        addressRepository.checkForAddress(requestDTO.getCity(), requestDTO.getStreet(), requestDTO.getHouseNr())
                .orElseThrow(() -> new RecordNotFoundException(requestDTO.toString()));
    }
}
