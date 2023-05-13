package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.JourneyStatusWest;
import ro.west.service.dto.JourneyStatusWestDTO;

/**
 * Mapper for the entity {@link JourneyStatusWest} and its DTO {@link JourneyStatusWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface JourneyStatusWestMapper extends EntityMapper<JourneyStatusWestDTO, JourneyStatusWest> {}
