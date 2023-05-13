package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.TrainTypeWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.TrainTypeWest}.
 */
public interface TrainTypeWestService {
    /**
     * Save a trainTypeWest.
     *
     * @param trainTypeWestDTO the entity to save.
     * @return the persisted entity.
     */
    TrainTypeWestDTO save(TrainTypeWestDTO trainTypeWestDTO);

    /**
     * Updates a trainTypeWest.
     *
     * @param trainTypeWestDTO the entity to update.
     * @return the persisted entity.
     */
    TrainTypeWestDTO update(TrainTypeWestDTO trainTypeWestDTO);

    /**
     * Partially updates a trainTypeWest.
     *
     * @param trainTypeWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainTypeWestDTO> partialUpdate(TrainTypeWestDTO trainTypeWestDTO);

    /**
     * Get all the trainTypeWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainTypeWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainTypeWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainTypeWestDTO> findOne(Long id);

    /**
     * Delete the "id" trainTypeWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
