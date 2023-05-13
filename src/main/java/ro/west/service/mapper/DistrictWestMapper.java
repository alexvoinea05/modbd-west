package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.DistrictWest;
import ro.west.service.dto.DistrictWestDTO;

/**
 * Mapper for the entity {@link DistrictWest} and its DTO {@link DistrictWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictWestMapper extends EntityMapper<DistrictWestDTO, DistrictWest> {}
