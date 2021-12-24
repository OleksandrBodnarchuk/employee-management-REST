package com.obodnarchuk.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
       }catch (RecordNotFoundException e){
           Address address = mapper.convertValue(requestDTO, Address.class);
           addressRepository.save(address);
           responseDTO = mapToResponseDTO(address);
       }
        return responseDTO;
    }

    @Override
    public void deleteAddressById(long id) {

    }

    @Override
    public AddressResponseDTO updateAddress(long id, AddressRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        return null;
    }


    private Address findAddressByIdOrThrow(long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    private AddressResponseDTO mapToResponseDTO(Address address) {
        return mapper.convertValue(address, AddressResponseDTO.class);
    }

    private void checkNewAddressOrThrow(AddressRequestDTO requestDTO) {
        addressRepository.checkForAddress(requestDTO.getCity(),requestDTO.getStreet(),requestDTO.getHouseNr())
                .orElseThrow(()-> new RecordNotFoundException(requestDTO.toString()));
    }
}
