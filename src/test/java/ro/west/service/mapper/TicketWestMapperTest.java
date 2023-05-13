package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketWestMapperTest {

    private TicketWestMapper ticketWestMapper;

    @BeforeEach
    public void setUp() {
        ticketWestMapper = new TicketWestMapperImpl();
    }
}
