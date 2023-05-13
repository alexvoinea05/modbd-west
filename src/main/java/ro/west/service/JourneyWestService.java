package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.JourneyWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.JourneyWest}.
 */
public interface JourneyWestService {
    /**
     * Save a journeyWest.
     *
     * @param journeyWestDTO the entity to save.
     * @return the persisted entity.
     */
    JourneyWestDTO save(JourneyWestDTO journeyWestDTO);

    /**
     * Updates a journeyWest.
     *
     * @param journeyWestDTO the entity to update.
     * @return the persisted entity.
     */
    JourneyWestDTO update(JourneyWestDTO journeyWestDTO);

    /**
     * Partially updates a journeyWest.
     *
     * @param journeyWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JourneyWestDTO> partialUpdate(JourneyWestDTO journeyWestDTO);

    /**
     * Get all the journeyWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourneyWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" journeyWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourneyWestDTO> findOne(Long id);

    /**
     * Delete the "id" journeyWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
