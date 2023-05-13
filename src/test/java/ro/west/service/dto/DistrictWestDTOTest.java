package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class DistrictWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictWestDTO.class);
        DistrictWestDTO districtWestDTO1 = new DistrictWestDTO();
        districtWestDTO1.setId(1L);
        DistrictWestDTO districtWestDTO2 = new DistrictWestDTO();
        assertThat(districtWestDTO1).isNotEqualTo(districtWestDTO2);
        districtWestDTO2.setId(districtWestDTO1.getId());
        assertThat(districtWestDTO1).isEqualTo(districtWestDTO2);
        districtWestDTO2.setId(2L);
        assertThat(districtWestDTO1).isNotEqualTo(districtWestDTO2);
        districtWestDTO1.setId(null);
        assertThat(districtWestDTO1).isNotEqualTo(districtWestDTO2);
    }
}
