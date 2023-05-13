package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressWestMapperTest {

    private AddressWestMapper addressWestMapper;

    @BeforeEach
    public void setUp() {
        addressWestMapper = new AddressWestMapperImpl();
    }
}
