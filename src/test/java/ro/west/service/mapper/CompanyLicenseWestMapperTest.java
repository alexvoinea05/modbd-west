package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyLicenseWestMapperTest {

    private CompanyLicenseWestMapper companyLicenseWestMapper;

    @BeforeEach
    public void setUp() {
        companyLicenseWestMapper = new CompanyLicenseWestMapperImpl();
    }
}
