package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JourneyStatusWestMapperTest {

    private JourneyStatusWestMapper journeyStatusWestMapper;

    @BeforeEach
    public void setUp() {
        journeyStatusWestMapper = new JourneyStatusWestMapperImpl();
    }
}
