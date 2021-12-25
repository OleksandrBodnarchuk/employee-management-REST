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
            checkNewAddressOrThrow(requestDTO);
            throw new RecordExistsException(requestDTO.toString());
        } catch (RecordNotFoundException e) {
            Address address = mapRequestToAddress(requestDTO);
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
        Address address;
        try {
            address = findAddressByIdOrThrow(id);
            checkDTOValuesAndMap(requestDTO, address);
        } catch (RecordNotFoundException e) {
            address = mapRequestToAddress(requestDTO);
        }
        addressRepository.save(address);
        return mapToResponseDTO(address);
    }

    private void checkDTOValuesAndMap(AddressRequestDTO requestDTO, Address address) {
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

    @Override
    public Optional<Address> findAddress(Address address) {
        if (address!=null){
            return addressRepository.checkForAddress(address.getCity(), address.getStreet(), address.getHouseNr());
        }
        return Optional.empty();
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

    private Address mapRequestToAddress(AddressRequestDTO requestDTO) {
        return mapper.convertValue(requestDTO, Address.class);
    }
}
