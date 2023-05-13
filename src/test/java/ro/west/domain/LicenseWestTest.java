package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class LicenseWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseWest.class);
        LicenseWest licenseWest1 = new LicenseWest();
        licenseWest1.setId(1L);
        LicenseWest licenseWest2 = new LicenseWest();
        licenseWest2.setId(licenseWest1.getId());
        assertThat(licenseWest1).isEqualTo(licenseWest2);
        licenseWest2.setId(2L);
        assertThat(licenseWest1).isNotEqualTo(licenseWest2);
        licenseWest1.setId(null);
        assertThat(licenseWest1).isNotEqualTo(licenseWest2);
    }
}
