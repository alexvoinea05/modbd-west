package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class RailwayTypeWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayTypeWestDTO.class);
        RailwayTypeWestDTO railwayTypeWestDTO1 = new RailwayTypeWestDTO();
        railwayTypeWestDTO1.setId(1L);
        RailwayTypeWestDTO railwayTypeWestDTO2 = new RailwayTypeWestDTO();
        assertThat(railwayTypeWestDTO1).isNotEqualTo(railwayTypeWestDTO2);
        railwayTypeWestDTO2.setId(railwayTypeWestDTO1.getId());
        assertThat(railwayTypeWestDTO1).isEqualTo(railwayTypeWestDTO2);
        railwayTypeWestDTO2.setId(2L);
        assertThat(railwayTypeWestDTO1).isNotEqualTo(railwayTypeWestDTO2);
        railwayTypeWestDTO1.setId(null);
        assertThat(railwayTypeWestDTO1).isNotEqualTo(railwayTypeWestDTO2);
    }
}
