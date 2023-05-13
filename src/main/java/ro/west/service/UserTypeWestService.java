package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.UserTypeWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.UserTypeWest}.
 */
public interface UserTypeWestService {
    /**
     * Save a userTypeWest.
     *
     * @param userTypeWestDTO the entity to save.
     * @return the persisted entity.
     */
    UserTypeWestDTO save(UserTypeWestDTO userTypeWestDTO);

    /**
     * Updates a userTypeWest.
     *
     * @param userTypeWestDTO the entity to update.
     * @return the persisted entity.
     */
    UserTypeWestDTO update(UserTypeWestDTO userTypeWestDTO);

    /**
     * Partially updates a userTypeWest.
     *
     * @param userTypeWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserTypeWestDTO> partialUpdate(UserTypeWestDTO userTypeWestDTO);

    /**
     * Get all the userTypeWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserTypeWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userTypeWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserTypeWestDTO> findOne(Long id);

    /**
     * Delete the "id" userTypeWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
