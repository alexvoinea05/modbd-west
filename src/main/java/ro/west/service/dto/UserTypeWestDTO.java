package ro.west.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.UserTypeWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserTypeWestDTO implements Serializable {

    private Long id;

    private String code;

    private Double discount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTypeWestDTO)) {
            return false;
        }

        UserTypeWestDTO userTypeWestDTO = (UserTypeWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userTypeWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTypeWestDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
