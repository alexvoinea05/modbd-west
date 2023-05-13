package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CityWestMapperTest {

    private CityWestMapper cityWestMapper;

    @BeforeEach
    public void setUp() {
        cityWestMapper = new CityWestMapperImpl();
    }
}
