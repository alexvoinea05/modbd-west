package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class AppUserWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserWest.class);
        AppUserWest appUserWest1 = new AppUserWest();
        appUserWest1.setId(1L);
        AppUserWest appUserWest2 = new AppUserWest();
        appUserWest2.setId(appUserWest1.getId());
        assertThat(appUserWest1).isEqualTo(appUserWest2);
        appUserWest2.setId(2L);
        assertThat(appUserWest1).isNotEqualTo(appUserWest2);
        appUserWest1.setId(null);
        assertThat(appUserWest1).isNotEqualTo(appUserWest2);
    }
}
