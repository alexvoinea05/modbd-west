package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.AddressWest;

/**
 * Spring Data JPA repository for the AddressWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressWestRepository extends JpaRepository<AddressWest, Long> {}
