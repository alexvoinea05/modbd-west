package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RailwayStationWestMapperTest {

    private RailwayStationWestMapper railwayStationWestMapper;

    @BeforeEach
    public void setUp() {
        railwayStationWestMapper = new RailwayStationWestMapperImpl();
    }
}
