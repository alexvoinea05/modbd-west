package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class CityWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityWest.class);
        CityWest cityWest1 = new CityWest();
        cityWest1.setId(1L);
        CityWest cityWest2 = new CityWest();
        cityWest2.setId(cityWest1.getId());
        assertThat(cityWest1).isEqualTo(cityWest2);
        cityWest2.setId(2L);
        assertThat(cityWest1).isNotEqualTo(cityWest2);
        cityWest1.setId(null);
        assertThat(cityWest1).isNotEqualTo(cityWest2);
    }
}
