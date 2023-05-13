package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class JourneyWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyWest.class);
        JourneyWest journeyWest1 = new JourneyWest();
        journeyWest1.setId(1L);
        JourneyWest journeyWest2 = new JourneyWest();
        journeyWest2.setId(journeyWest1.getId());
        assertThat(journeyWest1).isEqualTo(journeyWest2);
        journeyWest2.setId(2L);
        assertThat(journeyWest1).isNotEqualTo(journeyWest2);
        journeyWest1.setId(null);
        assertThat(journeyWest1).isNotEqualTo(journeyWest2);
    }
}
