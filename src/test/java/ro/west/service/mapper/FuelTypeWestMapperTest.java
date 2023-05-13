package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuelTypeWestMapperTest {

    private FuelTypeWestMapper fuelTypeWestMapper;

    @BeforeEach
    public void setUp() {
        fuelTypeWestMapper = new FuelTypeWestMapperImpl();
    }
}
