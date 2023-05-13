package ro.west.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.DistrictWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictWestDTO implements Serializable {

    private Long id;

    private String name;

    private String region;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictWestDTO)) {
            return false;
        }

        DistrictWestDTO districtWestDTO = (DistrictWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, districtWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictWestDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", region='" + getRegion() + "'" +
            "}";
    }
}
