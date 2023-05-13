package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class RailwayStationWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayStationWestDTO.class);
        RailwayStationWestDTO railwayStationWestDTO1 = new RailwayStationWestDTO();
        railwayStationWestDTO1.setId(1L);
        RailwayStationWestDTO railwayStationWestDTO2 = new RailwayStationWestDTO();
        assertThat(railwayStationWestDTO1).isNotEqualTo(railwayStationWestDTO2);
        railwayStationWestDTO2.setId(railwayStationWestDTO1.getId());
        assertThat(railwayStationWestDTO1).isEqualTo(railwayStationWestDTO2);
        railwayStationWestDTO2.setId(2L);
        assertThat(railwayStationWestDTO1).isNotEqualTo(railwayStationWestDTO2);
        railwayStationWestDTO1.setId(null);
        assertThat(railwayStationWestDTO1).isNotEqualTo(railwayStationWestDTO2);
    }
}
