package ro.west.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A CompanyLicenseWest.
 */
@Entity
@Table(name = "company_license_west")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyLicenseWest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "companyLicenseWests" }, allowSetters = true)
    private CompanyWest idCompany;

    @ManyToOne
    @JsonIgnoreProperties(value = { "companyLicenseWests" }, allowSetters = true)
    private LicenseWest idLicense;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyLicenseWest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyWest getIdCompany() {
        return this.idCompany;
    }

    public void setIdCompany(CompanyWest companyWest) {
        this.idCompany = companyWest;
    }

    public CompanyLicenseWest idCompany(CompanyWest companyWest) {
        this.setIdCompany(companyWest);
        return this;
    }

    public LicenseWest getIdLicense() {
        return this.idLicense;
    }

    public void setIdLicense(LicenseWest licenseWest) {
        this.idLicense = licenseWest;
    }

    public CompanyLicenseWest idLicense(LicenseWest licenseWest) {
        this.setIdLicense(licenseWest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyLicenseWest)) {
            return false;
        }
        return id != null && id.equals(((CompanyLicenseWest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyLicenseWest{" +
            "id=" + getId() +
            "}";
    }
}
