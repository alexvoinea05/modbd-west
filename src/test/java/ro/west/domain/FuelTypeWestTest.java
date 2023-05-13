package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class FuelTypeWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTypeWest.class);
        FuelTypeWest fuelTypeWest1 = new FuelTypeWest();
        fuelTypeWest1.setId(1L);
        FuelTypeWest fuelTypeWest2 = new FuelTypeWest();
        fuelTypeWest2.setId(fuelTypeWest1.getId());
        assertThat(fuelTypeWest1).isEqualTo(fuelTypeWest2);
        fuelTypeWest2.setId(2L);
        assertThat(fuelTypeWest1).isNotEqualTo(fuelTypeWest2);
        fuelTypeWest1.setId(null);
        assertThat(fuelTypeWest1).isNotEqualTo(fuelTypeWest2);
    }
}
