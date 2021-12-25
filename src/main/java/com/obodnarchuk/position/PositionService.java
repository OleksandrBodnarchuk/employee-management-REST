package com.obodnarchuk.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.obodnarchuk.position.PositionUtil.*;

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
        Position position = findPositionOrThrow(id,positionRepository);
        positionRepository.delete(position);
    }

    @Override
    public PositionResponseDTO updatePosition(long id, Position requestDTO) {
        Position position;
        try {
            position = findPositionOrThrow(id,positionRepository);
            position.setTitle(requestDTO.getTitle());
        } catch (RecordNotFoundException e) {
            position=requestDTO;
        }
        positionRepository.save(position);
        return mapToResponseDTO(position,mapper);
    }

    @Override
    public List<PositionResponseDTO> getAllPositions() {
        List<Position> positionsFromDB = positionRepository.findAll();
        return positionsFromDB.stream().map(e->mapToResponseDTO(e,mapper)).collect(Collectors.toList());
    }

    @Override
    public PositionResponseDTO getPositionById(long id) {
        return mapToResponseDTO(findPositionOrThrow(id,positionRepository),mapper);
    }

    @Override
    public PositionResponseDTO savePosition(Position request) {
        Position position = getPositionByTitle(request.getTitle(),positionRepository);
        if (position != null) {
            throw new RecordExistsException(position.getId());
        } else {
            positionRepository.save(position = new Position(request.getTitle()));
        }

        return mapToResponseDTO(position,mapper);
    }


    public Position getByTitle(String title) {
        return getPositionByTitle(title,positionRepository);
    }
}
