package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class RailwayStationWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayStationWest.class);
        RailwayStationWest railwayStationWest1 = new RailwayStationWest();
        railwayStationWest1.setId(1L);
        RailwayStationWest railwayStationWest2 = new RailwayStationWest();
        railwayStationWest2.setId(railwayStationWest1.getId());
        assertThat(railwayStationWest1).isEqualTo(railwayStationWest2);
        railwayStationWest2.setId(2L);
        assertThat(railwayStationWest1).isNotEqualTo(railwayStationWest2);
        railwayStationWest1.setId(null);
        assertThat(railwayStationWest1).isNotEqualTo(railwayStationWest2);
    }
}
