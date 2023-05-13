package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.TrainWest;

/**
 * Spring Data JPA repository for the TrainWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainWestRepository extends JpaRepository<TrainWest, Long> {}
