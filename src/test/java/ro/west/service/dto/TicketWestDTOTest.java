package ro.west.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class TicketWestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketWestDTO.class);
        TicketWestDTO ticketWestDTO1 = new TicketWestDTO();
        ticketWestDTO1.setId(1L);
        TicketWestDTO ticketWestDTO2 = new TicketWestDTO();
        assertThat(ticketWestDTO1).isNotEqualTo(ticketWestDTO2);
        ticketWestDTO2.setId(ticketWestDTO1.getId());
        assertThat(ticketWestDTO1).isEqualTo(ticketWestDTO2);
        ticketWestDTO2.setId(2L);
        assertThat(ticketWestDTO1).isNotEqualTo(ticketWestDTO2);
        ticketWestDTO1.setId(null);
        assertThat(ticketWestDTO1).isNotEqualTo(ticketWestDTO2);
    }
}
