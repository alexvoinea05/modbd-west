package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.TrainTypeWest;
import ro.west.service.dto.TrainTypeWestDTO;

/**
 * Mapper for the entity {@link TrainTypeWest} and its DTO {@link TrainTypeWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainTypeWestMapper extends EntityMapper<TrainTypeWestDTO, TrainTypeWest> {}
