package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JourneyWestMapperTest {

    private JourneyWestMapper journeyWestMapper;

    @BeforeEach
    public void setUp() {
        journeyWestMapper = new JourneyWestMapperImpl();
    }
}
