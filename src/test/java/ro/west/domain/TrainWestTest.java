package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class TrainWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainWest.class);
        TrainWest trainWest1 = new TrainWest();
        trainWest1.setId(1L);
        TrainWest trainWest2 = new TrainWest();
        trainWest2.setId(trainWest1.getId());
        assertThat(trainWest1).isEqualTo(trainWest2);
        trainWest2.setId(2L);
        assertThat(trainWest1).isNotEqualTo(trainWest2);
        trainWest1.setId(null);
        assertThat(trainWest1).isNotEqualTo(trainWest2);
    }
}
