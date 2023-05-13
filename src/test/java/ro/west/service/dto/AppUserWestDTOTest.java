package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class AppUserWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserWestDTO.class);
        AppUserWestDTO appUserWestDTO1 = new AppUserWestDTO();
        appUserWestDTO1.setId(1L);
        AppUserWestDTO appUserWestDTO2 = new AppUserWestDTO();
        assertThat(appUserWestDTO1).isNotEqualTo(appUserWestDTO2);
        appUserWestDTO2.setId(appUserWestDTO1.getId());
        assertThat(appUserWestDTO1).isEqualTo(appUserWestDTO2);
        appUserWestDTO2.setId(2L);
        assertThat(appUserWestDTO1).isNotEqualTo(appUserWestDTO2);
        appUserWestDTO1.setId(null);
        assertThat(appUserWestDTO1).isNotEqualTo(appUserWestDTO2);
    }
}
