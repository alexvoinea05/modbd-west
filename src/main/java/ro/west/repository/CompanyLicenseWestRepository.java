package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.CompanyLicenseWest;

/**
 * Spring Data JPA repository for the CompanyLicenseWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyLicenseWestRepository extends JpaRepository<CompanyLicenseWest, Long> {}
