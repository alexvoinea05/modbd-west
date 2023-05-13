package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.UserTypeWest;
import ro.west.service.dto.UserTypeWestDTO;

/**
 * Mapper for the entity {@link UserTypeWest} and its DTO {@link UserTypeWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserTypeWestMapper extends EntityMapper<UserTypeWestDTO, UserTypeWest> {}
