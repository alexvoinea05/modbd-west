package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.AddressWest;
import ro.west.service.dto.AddressWestDTO;

/**
 * Mapper for the entity {@link AddressWest} and its DTO {@link AddressWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressWestMapper extends EntityMapper<AddressWestDTO, AddressWest> {}
