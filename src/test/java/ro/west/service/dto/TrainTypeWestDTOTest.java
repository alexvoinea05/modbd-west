package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class TrainTypeWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainTypeWestDTO.class);
        TrainTypeWestDTO trainTypeWestDTO1 = new TrainTypeWestDTO();
        trainTypeWestDTO1.setId(1L);
        TrainTypeWestDTO trainTypeWestDTO2 = new TrainTypeWestDTO();
        assertThat(trainTypeWestDTO1).isNotEqualTo(trainTypeWestDTO2);
        trainTypeWestDTO2.setId(trainTypeWestDTO1.getId());
        assertThat(trainTypeWestDTO1).isEqualTo(trainTypeWestDTO2);
        trainTypeWestDTO2.setId(2L);
        assertThat(trainTypeWestDTO1).isNotEqualTo(trainTypeWestDTO2);
        trainTypeWestDTO1.setId(null);
        assertThat(trainTypeWestDTO1).isNotEqualTo(trainTypeWestDTO2);
    }
}
