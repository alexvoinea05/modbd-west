package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.JourneyStatusWest;

/**
 * Spring Data JPA repository for the JourneyStatusWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourneyStatusWestRepository extends JpaRepository<JourneyStatusWest, Long> {}
