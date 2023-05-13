package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class FuelTypeWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTypeWestDTO.class);
        FuelTypeWestDTO fuelTypeWestDTO1 = new FuelTypeWestDTO();
        fuelTypeWestDTO1.setId(1L);
        FuelTypeWestDTO fuelTypeWestDTO2 = new FuelTypeWestDTO();
        assertThat(fuelTypeWestDTO1).isNotEqualTo(fuelTypeWestDTO2);
        fuelTypeWestDTO2.setId(fuelTypeWestDTO1.getId());
        assertThat(fuelTypeWestDTO1).isEqualTo(fuelTypeWestDTO2);
        fuelTypeWestDTO2.setId(2L);
        assertThat(fuelTypeWestDTO1).isNotEqualTo(fuelTypeWestDTO2);
        fuelTypeWestDTO1.setId(null);
        assertThat(fuelTypeWestDTO1).isNotEqualTo(fuelTypeWestDTO2);
    }
}
