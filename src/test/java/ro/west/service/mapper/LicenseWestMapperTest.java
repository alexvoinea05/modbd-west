package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LicenseWestMapperTest {

    private LicenseWestMapper licenseWestMapper;

    @BeforeEach
    public void setUp() {
        licenseWestMapper = new LicenseWestMapperImpl();
    }
}
