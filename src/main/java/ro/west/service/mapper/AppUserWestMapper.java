package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.AppUserWest;
import ro.west.service.dto.AppUserWestDTO;

/**
 * Mapper for the entity {@link AppUserWest} and its DTO {@link AppUserWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserWestMapper extends EntityMapper<AppUserWestDTO, AppUserWest> {}
