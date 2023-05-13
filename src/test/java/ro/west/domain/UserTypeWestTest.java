package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class UserTypeWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTypeWest.class);
        UserTypeWest userTypeWest1 = new UserTypeWest();
        userTypeWest1.setId(1L);
        UserTypeWest userTypeWest2 = new UserTypeWest();
        userTypeWest2.setId(userTypeWest1.getId());
        assertThat(userTypeWest1).isEqualTo(userTypeWest2);
        userTypeWest2.setId(2L);
        assertThat(userTypeWest1).isNotEqualTo(userTypeWest2);
        userTypeWest1.setId(null);
        assertThat(userTypeWest1).isNotEqualTo(userTypeWest2);
    }
}
