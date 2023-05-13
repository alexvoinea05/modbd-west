package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class JourneyStatusWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyStatusWestDTO.class);
        JourneyStatusWestDTO journeyStatusWestDTO1 = new JourneyStatusWestDTO();
        journeyStatusWestDTO1.setId(1L);
        JourneyStatusWestDTO journeyStatusWestDTO2 = new JourneyStatusWestDTO();
        assertThat(journeyStatusWestDTO1).isNotEqualTo(journeyStatusWestDTO2);
        journeyStatusWestDTO2.setId(journeyStatusWestDTO1.getId());
        assertThat(journeyStatusWestDTO1).isEqualTo(journeyStatusWestDTO2);
        journeyStatusWestDTO2.setId(2L);
        assertThat(journeyStatusWestDTO1).isNotEqualTo(journeyStatusWestDTO2);
        journeyStatusWestDTO1.setId(null);
        assertThat(journeyStatusWestDTO1).isNotEqualTo(journeyStatusWestDTO2);
    }
}
