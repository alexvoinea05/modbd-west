package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class CityWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityWestDTO.class);
        CityWestDTO cityWestDTO1 = new CityWestDTO();
        cityWestDTO1.setId(1L);
        CityWestDTO cityWestDTO2 = new CityWestDTO();
        assertThat(cityWestDTO1).isNotEqualTo(cityWestDTO2);
        cityWestDTO2.setId(cityWestDTO1.getId());
        assertThat(cityWestDTO1).isEqualTo(cityWestDTO2);
        cityWestDTO2.setId(2L);
        assertThat(cityWestDTO1).isNotEqualTo(cityWestDTO2);
        cityWestDTO1.setId(null);
        assertThat(cityWestDTO1).isNotEqualTo(cityWestDTO2);
    }
}
