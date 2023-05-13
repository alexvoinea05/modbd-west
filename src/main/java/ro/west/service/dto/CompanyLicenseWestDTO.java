package ro.west.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.CompanyLicenseWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyLicenseWestDTO implements Serializable {

    private Long id;

    private CompanyWestDTO idCompany;

    private LicenseWestDTO idLicense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyWestDTO getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(CompanyWestDTO idCompany) {
        this.idCompany = idCompany;
    }

    public LicenseWestDTO getIdLicense() {
        return idLicense;
    }

    public void setIdLicense(LicenseWestDTO idLicense) {
        this.idLicense = idLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyLicenseWestDTO)) {
            return false;
        }

        CompanyLicenseWestDTO companyLicenseWestDTO = (CompanyLicenseWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyLicenseWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyLicenseWestDTO{" +
            "id=" + getId() +
            ", idCompany=" + getIdCompany() +
            ", idLicense=" + getIdLicense() +
            "}";
    }
}
