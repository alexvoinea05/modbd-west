package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class DistrictWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictWest.class);
        DistrictWest districtWest1 = new DistrictWest();
        districtWest1.setId(1L);
        DistrictWest districtWest2 = new DistrictWest();
        districtWest2.setId(districtWest1.getId());
        assertThat(districtWest1).isEqualTo(districtWest2);
        districtWest2.setId(2L);
        assertThat(districtWest1).isNotEqualTo(districtWest2);
        districtWest1.setId(null);
        assertThat(districtWest1).isNotEqualTo(districtWest2);
    }
}
