package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppUserWestMapperTest {

    private AppUserWestMapper appUserWestMapper;

    @BeforeEach
    public void setUp() {
        appUserWestMapper = new AppUserWestMapperImpl();
    }
}
