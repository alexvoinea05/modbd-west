package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistrictWestMapperTest {

    private DistrictWestMapper districtWestMapper;

    @BeforeEach
    public void setUp() {
        districtWestMapper = new DistrictWestMapperImpl();
    }
}
