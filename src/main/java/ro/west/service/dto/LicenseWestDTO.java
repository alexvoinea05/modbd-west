package ro.west.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.LicenseWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LicenseWestDTO implements Serializable {

    private Long id;

    private Long licenseNumber;

    private String licenseDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseDescription() {
        return licenseDescription;
    }

    public void setLicenseDescription(String licenseDescription) {
        this.licenseDescription = licenseDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseWestDTO)) {
            return false;
        }

        LicenseWestDTO licenseWestDTO = (LicenseWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, licenseWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenseWestDTO{" +
            "id=" + getId() +
            ", licenseNumber=" + getLicenseNumber() +
            ", licenseDescription='" + getLicenseDescription() + "'" +
            "}";
    }
}
