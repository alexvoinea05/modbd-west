package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.AppUserWest;

/**
 * Spring Data JPA repository for the AppUserWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserWestRepository extends JpaRepository<AppUserWest, Long> {}
