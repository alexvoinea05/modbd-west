package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.LicenseWest;

/**
 * Spring Data JPA repository for the LicenseWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenseWestRepository extends JpaRepository<LicenseWest, Long> {}
