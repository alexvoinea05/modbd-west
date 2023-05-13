package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class CompanyLicenseWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLicenseWest.class);
        CompanyLicenseWest companyLicenseWest1 = new CompanyLicenseWest();
        companyLicenseWest1.setId(1L);
        CompanyLicenseWest companyLicenseWest2 = new CompanyLicenseWest();
        companyLicenseWest2.setId(companyLicenseWest1.getId());
        assertThat(companyLicenseWest1).isEqualTo(companyLicenseWest2);
        companyLicenseWest2.setId(2L);
        assertThat(companyLicenseWest1).isNotEqualTo(companyLicenseWest2);
        companyLicenseWest1.setId(null);
        assertThat(companyLicenseWest1).isNotEqualTo(companyLicenseWest2);
    }
}
