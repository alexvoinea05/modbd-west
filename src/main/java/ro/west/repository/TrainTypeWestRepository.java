package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.TrainTypeWest;

/**
 * Spring Data JPA repository for the TrainTypeWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainTypeWestRepository extends JpaRepository<TrainTypeWest, Long> {}
