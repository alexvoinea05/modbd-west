package ro.west.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A LicenseWest.
 */
@Entity
@Table(name = "license_west")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LicenseWest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "license_number")
    private Long licenseNumber;

    @Column(name = "license_description")
    private String licenseDescription;

    @OneToMany(mappedBy = "idLicense")
    @JsonIgnoreProperties(value = { "idCompany", "idLicense" }, allowSetters = true)
    private Set<CompanyLicenseWest> companyLicenseWests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LicenseWest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseNumber() {
        return this.licenseNumber;
    }

    public LicenseWest licenseNumber(Long licenseNumber) {
        this.setLicenseNumber(licenseNumber);
        return this;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseDescription() {
        return this.licenseDescription;
    }

    public LicenseWest licenseDescription(String licenseDescription) {
        this.setLicenseDescription(licenseDescription);
        return this;
    }

    public void setLicenseDescription(String licenseDescription) {
        this.licenseDescription = licenseDescription;
    }

    public Set<CompanyLicenseWest> getCompanyLicenseWests() {
        return this.companyLicenseWests;
    }

    public void setCompanyLicenseWests(Set<CompanyLicenseWest> companyLicenseWests) {
        if (this.companyLicenseWests != null) {
            this.companyLicenseWests.forEach(i -> i.setIdLicense(null));
        }
        if (companyLicenseWests != null) {
            companyLicenseWests.forEach(i -> i.setIdLicense(this));
        }
        this.companyLicenseWests = companyLicenseWests;
    }

    public LicenseWest companyLicenseWests(Set<CompanyLicenseWest> companyLicenseWests) {
        this.setCompanyLicenseWests(companyLicenseWests);
        return this;
    }

    public LicenseWest addCompanyLicenseWest(CompanyLicenseWest companyLicenseWest) {
        this.companyLicenseWests.add(companyLicenseWest);
        companyLicenseWest.setIdLicense(this);
        return this;
    }

    public LicenseWest removeCompanyLicenseWest(CompanyLicenseWest companyLicenseWest) {
        this.companyLicenseWests.remove(companyLicenseWest);
        companyLicenseWest.setIdLicense(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseWest)) {
            return false;
        }
        return id != null && id.equals(((LicenseWest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenseWest{" +
            "id=" + getId() +
            ", licenseNumber=" + getLicenseNumber() +
            ", licenseDescription='" + getLicenseDescription() + "'" +
            "}";
    }
}
