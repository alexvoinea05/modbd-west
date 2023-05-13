package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class JourneyWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyWestDTO.class);
        JourneyWestDTO journeyWestDTO1 = new JourneyWestDTO();
        journeyWestDTO1.setId(1L);
        JourneyWestDTO journeyWestDTO2 = new JourneyWestDTO();
        assertThat(journeyWestDTO1).isNotEqualTo(journeyWestDTO2);
        journeyWestDTO2.setId(journeyWestDTO1.getId());
        assertThat(journeyWestDTO1).isEqualTo(journeyWestDTO2);
        journeyWestDTO2.setId(2L);
        assertThat(journeyWestDTO1).isNotEqualTo(journeyWestDTO2);
        journeyWestDTO1.setId(null);
        assertThat(journeyWestDTO1).isNotEqualTo(journeyWestDTO2);
    }
}
