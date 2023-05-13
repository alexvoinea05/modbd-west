package ro.west.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A CompanyWest.
 */
@Entity
@Table(name = "company_west")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyWest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "identification_number")
    private String identificationNumber;

    @OneToMany(mappedBy = "idCompany")
    @JsonIgnoreProperties(value = { "idCompany", "idLicense" }, allowSetters = true)
    private Set<CompanyLicenseWest> companyLicenseWests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyWest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CompanyWest name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    public CompanyWest identificationNumber(String identificationNumber) {
        this.setIdentificationNumber(identificationNumber);
        return this;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Set<CompanyLicenseWest> getCompanyLicenseWests() {
        return this.companyLicenseWests;
    }

    public void setCompanyLicenseWests(Set<CompanyLicenseWest> companyLicenseWests) {
        if (this.companyLicenseWests != null) {
            this.companyLicenseWests.forEach(i -> i.setIdCompany(null));
        }
        if (companyLicenseWests != null) {
            companyLicenseWests.forEach(i -> i.setIdCompany(this));
        }
        this.companyLicenseWests = companyLicenseWests;
    }

    public CompanyWest companyLicenseWests(Set<CompanyLicenseWest> companyLicenseWests) {
        this.setCompanyLicenseWests(companyLicenseWests);
        return this;
    }

    public CompanyWest addCompanyLicenseWest(CompanyLicenseWest companyLicenseWest) {
        this.companyLicenseWests.add(companyLicenseWest);
        companyLicenseWest.setIdCompany(this);
        return this;
    }

    public CompanyWest removeCompanyLicenseWest(CompanyLicenseWest companyLicenseWest) {
        this.companyLicenseWests.remove(companyLicenseWest);
        companyLicenseWest.setIdCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyWest)) {
            return false;
        }
        return id != null && id.equals(((CompanyWest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyWest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            "}";
    }
}
