package com.obodnarchuk.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordExistsException;
import com.obodnarchuk.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public PositionResponseDTO updatePosition(long id, Position requestDTO) {
        Position position;
        try {
            position = findPositionOrThrow(id);
            position.setTitle(requestDTO.getTitle());
        } catch (RecordNotFoundException e) {
            position=requestDTO;
        }
        positionRepository.save(position);
        return mapToResponseDTO(position);
    }

    @Override
    public List<PositionResponseDTO> getAllPositions() {
        List<Position> positionsFromDB = positionRepository.findAll();
        return positionsFromDB.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public PositionResponseDTO getPositionById(long id) {
        return mapToResponseDTO(findPositionOrThrow(id));
    }

    @Override
    public PositionResponseDTO savePosition(Position request) {
        Position position = getPositionByTitle(request.getTitle());
        if (position != null) {
            throw new RecordExistsException(position.getId());
        } else {
            positionRepository.save(position = new Position(request.getTitle()));
        }

        return mapToResponseDTO(position);
    }

    public Position getPositionByTitle(String title) {
        return positionRepository.findPositionByTitle(title);
    }

    private Position findPositionOrThrow(long id) {
        return positionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    private PositionResponseDTO mapToResponseDTO(Position position) {
        return mapper.convertValue(position, PositionResponseDTO.class);
    }
}
