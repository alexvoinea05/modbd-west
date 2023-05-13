package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class LicenseWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseWestDTO.class);
        LicenseWestDTO licenseWestDTO1 = new LicenseWestDTO();
        licenseWestDTO1.setId(1L);
        LicenseWestDTO licenseWestDTO2 = new LicenseWestDTO();
        assertThat(licenseWestDTO1).isNotEqualTo(licenseWestDTO2);
        licenseWestDTO2.setId(licenseWestDTO1.getId());
        assertThat(licenseWestDTO1).isEqualTo(licenseWestDTO2);
        licenseWestDTO2.setId(2L);
        assertThat(licenseWestDTO1).isNotEqualTo(licenseWestDTO2);
        licenseWestDTO1.setId(null);
        assertThat(licenseWestDTO1).isNotEqualTo(licenseWestDTO2);
    }
}
