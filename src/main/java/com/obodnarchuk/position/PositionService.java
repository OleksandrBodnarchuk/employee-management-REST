package com.obodnarchuk.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PositionService implements IPositionService {

    final ObjectMapper mapper;
    final PositionRepository positionRepository;

    public PositionService(ObjectMapper mapper, PositionRepository positionRepository) {
        this.mapper = mapper;
        this.positionRepository = positionRepository;
    }

    @Override
    public void deletePositionById(long id) {
        Position position = findPositionOrThrow(id);
        positionRepository.delete(position);
    }

    @Override
    public PositionResponseDTO savePosition(Position request) {
        Position position =  positionRepository.findPositionByTitle(request.getTitle());
        if (position!=null){
            throw new RecordExistsException(position.getId());
        }else {
         positionRepository.save(position=new Position(request.getTitle()));
        }

        return mapToResponseDTO(position);
    }

    private Position findPositionOrThrow(long id) {
        return positionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    private PositionResponseDTO mapToResponseDTO(Position position) {
        return mapper.convertValue(position, PositionResponseDTO.class);
    }
}
