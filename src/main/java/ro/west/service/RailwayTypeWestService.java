package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.RailwayTypeWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.RailwayTypeWest}.
 */
public interface RailwayTypeWestService {
    /**
     * Save a railwayTypeWest.
     *
     * @param railwayTypeWestDTO the entity to save.
     * @return the persisted entity.
     */
    RailwayTypeWestDTO save(RailwayTypeWestDTO railwayTypeWestDTO);

    /**
     * Updates a railwayTypeWest.
     *
     * @param railwayTypeWestDTO the entity to update.
     * @return the persisted entity.
     */
    RailwayTypeWestDTO update(RailwayTypeWestDTO railwayTypeWestDTO);

    /**
     * Partially updates a railwayTypeWest.
     *
     * @param railwayTypeWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RailwayTypeWestDTO> partialUpdate(RailwayTypeWestDTO railwayTypeWestDTO);

    /**
     * Get all the railwayTypeWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RailwayTypeWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" railwayTypeWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RailwayTypeWestDTO> findOne(Long id);

    /**
     * Delete the "id" railwayTypeWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
