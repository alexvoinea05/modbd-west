package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class CompanyLicenseWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLicenseWestDTO.class);
        CompanyLicenseWestDTO companyLicenseWestDTO1 = new CompanyLicenseWestDTO();
        companyLicenseWestDTO1.setId(1L);
        CompanyLicenseWestDTO companyLicenseWestDTO2 = new CompanyLicenseWestDTO();
        assertThat(companyLicenseWestDTO1).isNotEqualTo(companyLicenseWestDTO2);
        companyLicenseWestDTO2.setId(companyLicenseWestDTO1.getId());
        assertThat(companyLicenseWestDTO1).isEqualTo(companyLicenseWestDTO2);
        companyLicenseWestDTO2.setId(2L);
        assertThat(companyLicenseWestDTO1).isNotEqualTo(companyLicenseWestDTO2);
        companyLicenseWestDTO1.setId(null);
        assertThat(companyLicenseWestDTO1).isNotEqualTo(companyLicenseWestDTO2);
    }
}
