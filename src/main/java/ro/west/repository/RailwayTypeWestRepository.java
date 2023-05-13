package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.RailwayTypeWest;

/**
 * Spring Data JPA repository for the RailwayTypeWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RailwayTypeWestRepository extends JpaRepository<RailwayTypeWest, Long> {}
