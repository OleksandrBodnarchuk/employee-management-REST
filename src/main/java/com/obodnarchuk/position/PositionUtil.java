package com.obodnarchuk.position;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obodnarchuk.exceptions.RecordNotFoundException;

public class PositionUtil
{
    public static Position getPositionByTitle(String title,PositionRepository positionRepository) {
        return positionRepository.findPositionByTitle(title);
    }

    protected static Position findPositionOrThrow(long id,PositionRepository positionRepository) {
        return positionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    protected static PositionResponseDTO mapToResponseDTO(Position position, ObjectMapper mapper) {
        return mapper.convertValue(position, PositionResponseDTO.class);
    }
}
