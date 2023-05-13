package ro.west.service.mapper;

import org.mapstruct.*;
import ro.west.domain.CompanyLicenseWest;
import ro.west.domain.CompanyWest;
import ro.west.domain.LicenseWest;
import ro.west.service.dto.CompanyLicenseWestDTO;
import ro.west.service.dto.CompanyWestDTO;
import ro.west.service.dto.LicenseWestDTO;

/**
 * Mapper for the entity {@link CompanyLicenseWest} and its DTO {@link CompanyLicenseWestDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyLicenseWestMapper extends EntityMapper<CompanyLicenseWestDTO, CompanyLicenseWest> {
    @Mapping(target = "idCompany", source = "idCompany", qualifiedByName = "companyWestId")
    @Mapping(target = "idLicense", source = "idLicense", qualifiedByName = "licenseWestId")
    CompanyLicenseWestDTO toDto(CompanyLicenseWest s);

    @Named("companyWestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyWestDTO toDtoCompanyWestId(CompanyWest companyWest);

    @Named("licenseWestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LicenseWestDTO toDtoLicenseWestId(LicenseWest licenseWest);
}
