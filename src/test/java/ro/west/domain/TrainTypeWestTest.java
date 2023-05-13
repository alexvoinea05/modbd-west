package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class TrainTypeWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainTypeWest.class);
        TrainTypeWest trainTypeWest1 = new TrainTypeWest();
        trainTypeWest1.setId(1L);
        TrainTypeWest trainTypeWest2 = new TrainTypeWest();
        trainTypeWest2.setId(trainTypeWest1.getId());
        assertThat(trainTypeWest1).isEqualTo(trainTypeWest2);
        trainTypeWest2.setId(2L);
        assertThat(trainTypeWest1).isNotEqualTo(trainTypeWest2);
        trainTypeWest1.setId(null);
        assertThat(trainTypeWest1).isNotEqualTo(trainTypeWest2);
    }
}
