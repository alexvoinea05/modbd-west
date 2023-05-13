package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.CityWest;
import ro.west.service.dto.CityWestDTO;

/**
 * Mapper for the entity {@link CityWest} and its DTO {@link CityWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityWestMapper extends EntityMapper<CityWestDTO, CityWest> {}
