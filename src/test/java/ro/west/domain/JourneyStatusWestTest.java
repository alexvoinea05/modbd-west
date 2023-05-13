package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class JourneyStatusWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyStatusWest.class);
        JourneyStatusWest journeyStatusWest1 = new JourneyStatusWest();
        journeyStatusWest1.setId(1L);
        JourneyStatusWest journeyStatusWest2 = new JourneyStatusWest();
        journeyStatusWest2.setId(journeyStatusWest1.getId());
        assertThat(journeyStatusWest1).isEqualTo(journeyStatusWest2);
        journeyStatusWest2.setId(2L);
        assertThat(journeyStatusWest1).isNotEqualTo(journeyStatusWest2);
        journeyStatusWest1.setId(null);
        assertThat(journeyStatusWest1).isNotEqualTo(journeyStatusWest2);
    }
}
