package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.CityWest;

/**
 * Spring Data JPA repository for the CityWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityWestRepository extends JpaRepository<CityWest, Long> {}
