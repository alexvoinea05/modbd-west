package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.DistrictWest;

/**
 * Spring Data JPA repository for the DistrictWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictWestRepository extends JpaRepository<DistrictWest, Long> {}
