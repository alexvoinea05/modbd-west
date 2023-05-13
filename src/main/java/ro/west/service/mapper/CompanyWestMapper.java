package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.CompanyWest;
import ro.west.service.dto.CompanyWestDTO;

/**
 * Mapper for the entity {@link CompanyWest} and its DTO {@link CompanyWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyWestMapper extends EntityMapper<CompanyWestDTO, CompanyWest> {}
