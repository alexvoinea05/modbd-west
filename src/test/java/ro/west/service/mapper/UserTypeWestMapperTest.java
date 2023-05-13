package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTypeWestMapperTest {

    private UserTypeWestMapper userTypeWestMapper;

    @BeforeEach
    public void setUp() {
        userTypeWestMapper = new UserTypeWestMapperImpl();
    }
}
