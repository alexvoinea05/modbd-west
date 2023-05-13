package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainTypeWestMapperTest {

    private TrainTypeWestMapper trainTypeWestMapper;

    @BeforeEach
    public void setUp() {
        trainTypeWestMapper = new TrainTypeWestMapperImpl();
    }
}
