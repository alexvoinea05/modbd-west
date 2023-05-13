package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.CompanyWest;

/**
 * Spring Data JPA repository for the CompanyWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyWestRepository extends JpaRepository<CompanyWest, Long> {}
