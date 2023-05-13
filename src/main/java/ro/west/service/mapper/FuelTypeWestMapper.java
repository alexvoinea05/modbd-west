package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.FuelTypeWest;
import ro.west.service.dto.FuelTypeWestDTO;

/**
 * Mapper for the entity {@link FuelTypeWest} and its DTO {@link FuelTypeWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuelTypeWestMapper extends EntityMapper<FuelTypeWestDTO, FuelTypeWest> {}
