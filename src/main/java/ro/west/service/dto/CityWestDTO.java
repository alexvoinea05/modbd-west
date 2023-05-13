package ro.west.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.CityWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityWestDTO implements Serializable {

    private Long id;

    private String name;

    private Long districtId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityWestDTO)) {
            return false;
        }

        CityWestDTO cityWestDTO = (CityWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cityWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityWestDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", districtId=" + getDistrictId() +
            "}";
    }
}
