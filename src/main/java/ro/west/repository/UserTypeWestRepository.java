package ro.west.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.west.domain.UserTypeWest;

/**
 * Spring Data JPA repository for the UserTypeWest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTypeWestRepository extends JpaRepository<UserTypeWest, Long> {}
