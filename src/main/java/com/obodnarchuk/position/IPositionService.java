package com.obodnarchuk.position;


public interface IPositionService {
    PositionResponseDTO savePosition(Position requestDTO);

    void deletePositionById(long id);
}
