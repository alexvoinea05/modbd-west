package ro.west.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.AppUserWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserWestDTO implements Serializable {

    private Long id;

    private String password;

    private String cnp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserWestDTO)) {
            return false;
        }

        AppUserWestDTO appUserWestDTO = (AppUserWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appUserWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserWestDTO{" +
            "id=" + getId() +
            ", password='" + getPassword() + "'" +
            ", cnp='" + getCnp() + "'" +
            "}";
    }
}
