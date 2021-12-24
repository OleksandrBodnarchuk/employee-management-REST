package com.obodnarchuk.position;


import java.util.List;

public interface IPositionService {
    PositionResponseDTO savePosition(Position requestDTO);

    void deletePositionById(long id);

    PositionResponseDTO updatePosition(long id, Position requestDTO);

    List<PositionResponseDTO> getAllPositions();
}
