package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.TrainWest;
import ro.west.service.dto.TrainWestDTO;

/**
 * Mapper for the entity {@link TrainWest} and its DTO {@link TrainWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainWestMapper extends EntityMapper<TrainWestDTO, TrainWest> {}
