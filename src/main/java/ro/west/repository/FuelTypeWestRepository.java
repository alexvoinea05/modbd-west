package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.FuelTypeWest;

/**
 * Spring Data JPA repository for the FuelTypeWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTypeWestRepository extends JpaRepository<FuelTypeWest, Long> {}
