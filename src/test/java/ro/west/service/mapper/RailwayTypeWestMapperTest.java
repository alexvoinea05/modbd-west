package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RailwayTypeWestMapperTest {

    private RailwayTypeWestMapper railwayTypeWestMapper;

    @BeforeEach
    public void setUp() {
        railwayTypeWestMapper = new RailwayTypeWestMapperImpl();
    }
}
