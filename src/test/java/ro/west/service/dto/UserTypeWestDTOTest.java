package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class UserTypeWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTypeWestDTO.class);
        UserTypeWestDTO userTypeWestDTO1 = new UserTypeWestDTO();
        userTypeWestDTO1.setId(1L);
        UserTypeWestDTO userTypeWestDTO2 = new UserTypeWestDTO();
        assertThat(userTypeWestDTO1).isNotEqualTo(userTypeWestDTO2);
        userTypeWestDTO2.setId(userTypeWestDTO1.getId());
        assertThat(userTypeWestDTO1).isEqualTo(userTypeWestDTO2);
        userTypeWestDTO2.setId(2L);
        assertThat(userTypeWestDTO1).isNotEqualTo(userTypeWestDTO2);
        userTypeWestDTO1.setId(null);
        assertThat(userTypeWestDTO1).isNotEqualTo(userTypeWestDTO2);
    }
}
