package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.LicenseWest;
import ro.west.service.dto.LicenseWestDTO;

/**
 * Mapper for the entity {@link LicenseWest} and its DTO {@link LicenseWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface LicenseWestMapper extends EntityMapper<LicenseWestDTO, LicenseWest> {}
