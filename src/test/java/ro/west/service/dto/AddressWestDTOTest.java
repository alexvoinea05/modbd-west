package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class AddressWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressWestDTO.class);
        AddressWestDTO addressWestDTO1 = new AddressWestDTO();
        addressWestDTO1.setId(1L);
        AddressWestDTO addressWestDTO2 = new AddressWestDTO();
        assertThat(addressWestDTO1).isNotEqualTo(addressWestDTO2);
        addressWestDTO2.setId(addressWestDTO1.getId());
        assertThat(addressWestDTO1).isEqualTo(addressWestDTO2);
        addressWestDTO2.setId(2L);
        assertThat(addressWestDTO1).isNotEqualTo(addressWestDTO2);
        addressWestDTO1.setId(null);
        assertThat(addressWestDTO1).isNotEqualTo(addressWestDTO2);
    }
}
