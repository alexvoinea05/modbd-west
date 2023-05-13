package ro.west.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.west.web.rest.TestUtil;

class TicketWestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketWest.class);
        TicketWest ticketWest1 = new TicketWest();
        ticketWest1.setId(1L);
        TicketWest ticketWest2 = new TicketWest();
        ticketWest2.setId(ticketWest1.getId());
        assertThat(ticketWest1).isEqualTo(ticketWest2);
        ticketWest2.setId(2L);
        assertThat(ticketWest1).isNotEqualTo(ticketWest2);
        ticketWest1.setId(null);
        assertThat(ticketWest1).isNotEqualTo(ticketWest2);
    }
}
