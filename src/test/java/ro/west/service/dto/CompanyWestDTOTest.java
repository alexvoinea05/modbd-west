package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class CompanyWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyWestDTO.class);
        CompanyWestDTO companyWestDTO1 = new CompanyWestDTO();
        companyWestDTO1.setId(1L);
        CompanyWestDTO companyWestDTO2 = new CompanyWestDTO();
        assertThat(companyWestDTO1).isNotEqualTo(companyWestDTO2);
        companyWestDTO2.setId(companyWestDTO1.getId());
        assertThat(companyWestDTO1).isEqualTo(companyWestDTO2);
        companyWestDTO2.setId(2L);
        assertThat(companyWestDTO1).isNotEqualTo(companyWestDTO2);
        companyWestDTO1.setId(null);
        assertThat(companyWestDTO1).isNotEqualTo(companyWestDTO2);
    }
}
