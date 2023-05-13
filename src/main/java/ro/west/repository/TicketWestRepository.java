package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.TicketWest;

/**
 * Spring Data JPA repository for the TicketWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketWestRepository extends JpaRepository<TicketWest, Long> {}
