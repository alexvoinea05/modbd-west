package ro.west.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyWestMapperTest {

    private CompanyWestMapper companyWestMapper;

    @BeforeEach
    public void setUp() {
        companyWestMapper = new CompanyWestMapperImpl();
    }
}
