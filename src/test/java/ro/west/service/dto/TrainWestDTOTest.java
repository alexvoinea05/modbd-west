package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class TrainWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainWestDTO.class);
        TrainWestDTO trainWestDTO1 = new TrainWestDTO();
        trainWestDTO1.setId(1L);
        TrainWestDTO trainWestDTO2 = new TrainWestDTO();
        assertThat(trainWestDTO1).isNotEqualTo(trainWestDTO2);
        trainWestDTO2.setId(trainWestDTO1.getId());
        assertThat(trainWestDTO1).isEqualTo(trainWestDTO2);
        trainWestDTO2.setId(2L);
        assertThat(trainWestDTO1).isNotEqualTo(trainWestDTO2);
        trainWestDTO1.setId(null);
        assertThat(trainWestDTO1).isNotEqualTo(trainWestDTO2);
    }
}
