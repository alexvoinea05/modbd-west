package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class CompanyWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyWest.class);
        CompanyWest companyWest1 = new CompanyWest();
        companyWest1.setId(1L);
        CompanyWest companyWest2 = new CompanyWest();
        companyWest2.setId(companyWest1.getId());
        assertThat(companyWest1).isEqualTo(companyWest2);
        companyWest2.setId(2L);
        assertThat(companyWest1).isNotEqualTo(companyWest2);
        companyWest1.setId(null);
        assertThat(companyWest1).isNotEqualTo(companyWest2);
    }
}
