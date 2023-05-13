package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.RailwayTypeWest;
import ro.west.service.dto.RailwayTypeWestDTO;

/**
 * Mapper for the entity {@link RailwayTypeWest} and its DTO {@link RailwayTypeWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface RailwayTypeWestMapper extends EntityMapper<RailwayTypeWestDTO, RailwayTypeWest> {}
