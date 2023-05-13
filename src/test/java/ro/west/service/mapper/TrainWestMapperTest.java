package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainWestMapperTest {

    private TrainWestMapper trainWestMapper;

    @BeforeEach
    public void setUp() {
        trainWestMapper = new TrainWestMapperImpl();
    }
}
