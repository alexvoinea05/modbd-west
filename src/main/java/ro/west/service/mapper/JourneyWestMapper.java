package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.JourneyWest;
import ro.west.service.dto.JourneyWestDTO;

/**
 * Mapper for the entity {@link JourneyWest} and its DTO {@link JourneyWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface JourneyWestMapper extends EntityMapper<JourneyWestDTO, JourneyWest> {}
