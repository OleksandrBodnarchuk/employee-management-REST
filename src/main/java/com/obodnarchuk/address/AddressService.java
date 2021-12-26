package com.obodnarchuk.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
            AddressUtil.checkNewAddressOrThrow(requestDTO, addressRepository);
            throw new RecordExistsException(requestDTO.toString());
        } catch (RecordNotFoundException e) {
            Address address = AddressUtil.mapRequestToAddress(requestDTO, mapper);
            addressRepository.save(address);

            responseDTO = AddressUtil.mapToResponseDTO(address, mapper);
        }
        return responseDTO;
    }

    @Override
    public void deleteAddressById(long id) {
        Address address = AddressUtil.findAddressByIdOrThrow(id, addressRepository);
        addressRepository.delete(address);
    }

    @Override
    public AddressResponseDTO updateAddress(long id, AddressRequestDTO requestDTO) {
        Address address;
        try {
            address = AddressUtil.findAddressByIdOrThrow(id, addressRepository);
            AddressUtil.checkDTOValuesAndMap(requestDTO, address);
        } catch (RecordNotFoundException e) {
            address = AddressUtil.mapRequestToAddress(requestDTO, mapper);
        }
        addressRepository.save(address);
        return AddressUtil.mapToResponseDTO(address, mapper);
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        List<Address> addressesFromDB = addressRepository.findAll();
        return addressesFromDB.stream().map(e -> AddressUtil.mapToResponseDTO(e, mapper)).collect(Collectors.toList());
    }

    @Override
    public AddressResponseDTO getAddressById(long id) {
        Address address = AddressUtil.findAddressByIdOrThrow(id, addressRepository);
        return AddressUtil.mapToResponseDTO(address, mapper);
    }

    @Override
    public Optional<Address> findAddress(Address address) {
        if (address != null) {
            return addressRepository.checkForAddress(address.getCity(), address.getStreet(), address.getHouseNr());
        }
        return Optional.empty();
    }
}
