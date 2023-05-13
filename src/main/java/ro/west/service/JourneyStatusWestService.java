package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.JourneyStatusWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.JourneyStatusWest}.
 */
public interface JourneyStatusWestService {
    /**
     * Save a journeyStatusWest.
     *
     * @param journeyStatusWestDTO the entity to save.
     * @return the persisted entity.
     */
    JourneyStatusWestDTO save(JourneyStatusWestDTO journeyStatusWestDTO);

    /**
     * Updates a journeyStatusWest.
     *
     * @param journeyStatusWestDTO the entity to update.
     * @return the persisted entity.
     */
    JourneyStatusWestDTO update(JourneyStatusWestDTO journeyStatusWestDTO);

    /**
     * Partially updates a journeyStatusWest.
     *
     * @param journeyStatusWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JourneyStatusWestDTO> partialUpdate(JourneyStatusWestDTO journeyStatusWestDTO);

    /**
     * Get all the journeyStatusWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourneyStatusWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" journeyStatusWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourneyStatusWestDTO> findOne(Long id);

    /**
     * Delete the "id" journeyStatusWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
