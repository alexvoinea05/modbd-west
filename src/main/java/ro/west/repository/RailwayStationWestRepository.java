package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.RailwayStationWest;

/**
 * Spring Data JPA repository for the RailwayStationWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RailwayStationWestRepository extends JpaRepository<RailwayStationWest, Long> {}
