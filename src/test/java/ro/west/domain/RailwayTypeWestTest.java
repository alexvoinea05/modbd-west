package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class RailwayTypeWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayTypeWest.class);
        RailwayTypeWest railwayTypeWest1 = new RailwayTypeWest();
        railwayTypeWest1.setId(1L);
        RailwayTypeWest railwayTypeWest2 = new RailwayTypeWest();
        railwayTypeWest2.setId(railwayTypeWest1.getId());
        assertThat(railwayTypeWest1).isEqualTo(railwayTypeWest2);
        railwayTypeWest2.setId(2L);
        assertThat(railwayTypeWest1).isNotEqualTo(railwayTypeWest2);
        railwayTypeWest1.setId(null);
        assertThat(railwayTypeWest1).isNotEqualTo(railwayTypeWest2);
    }
}
