package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.JourneyWest;

/**
 * Spring Data JPA repository for the JourneyWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourneyWestRepository extends JpaRepository<JourneyWest, Long> {}
