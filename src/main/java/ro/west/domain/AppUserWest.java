package ro.west.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A AppUserWest.
 */
@Entity
@Table(name = "app_user_west")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserWest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "cnp")
    private String cnp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUserWest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public AppUserWest password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCnp() {
        return this.cnp;
    }

    public AppUserWest cnp(String cnp) {
        this.setCnp(cnp);
        return this;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserWest)) {
            return false;
        }
        return id != null && id.equals(((AppUserWest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserWest{" +
            "id=" + getId() +
            ", password='" + getPassword() + "'" +
            ", cnp='" + getCnp() + "'" +
            "}";
    }
}
