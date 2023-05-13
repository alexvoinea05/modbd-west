package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.TrainWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.TrainWest}.
 */
public interface TrainWestService {
    /**
     * Save a trainWest.
     *
     * @param trainWestDTO the entity to save.
     * @return the persisted entity.
     */
    TrainWestDTO save(TrainWestDTO trainWestDTO);

    /**
     * Updates a trainWest.
     *
     * @param trainWestDTO the entity to update.
     * @return the persisted entity.
     */
    TrainWestDTO update(TrainWestDTO trainWestDTO);

    /**
     * Partially updates a trainWest.
     *
     * @param trainWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainWestDTO> partialUpdate(TrainWestDTO trainWestDTO);

    /**
     * Get all the trainWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainWestDTO> findOne(Long id);

    /**
     * Delete the "id" trainWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
