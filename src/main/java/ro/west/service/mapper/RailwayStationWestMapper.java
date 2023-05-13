package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.RailwayStationWest;
import ro.west.service.dto.RailwayStationWestDTO;

/**
 * Mapper for the entity {@link RailwayStationWest} and its DTO {@link RailwayStationWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface RailwayStationWestMapper extends EntityMapper<RailwayStationWestDTO, RailwayStationWest> {}
