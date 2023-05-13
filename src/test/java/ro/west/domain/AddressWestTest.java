package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class AddressWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressWest.class);
        AddressWest addressWest1 = new AddressWest();
        addressWest1.setId(1L);
        AddressWest addressWest2 = new AddressWest();
        addressWest2.setId(addressWest1.getId());
        assertThat(addressWest1).isEqualTo(addressWest2);
        addressWest2.setId(2L);
        assertThat(addressWest1).isNotEqualTo(addressWest2);
        addressWest1.setId(null);
        assertThat(addressWest1).isNotEqualTo(addressWest2);
    }
}
