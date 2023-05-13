package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.CityWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.CityWest}.
 */
public interface CityWestService {
    /**
     * Save a cityWest.
     *
     * @param cityWestDTO the entity to save.
     * @return the persisted entity.
     */
    CityWestDTO save(CityWestDTO cityWestDTO);

    /**
     * Updates a cityWest.
     *
     * @param cityWestDTO the entity to update.
     * @return the persisted entity.
     */
    CityWestDTO update(CityWestDTO cityWestDTO);

    /**
     * Partially updates a cityWest.
     *
     * @param cityWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CityWestDTO> partialUpdate(CityWestDTO cityWestDTO);

    /**
     * Get all the cityWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CityWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cityWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CityWestDTO> findOne(Long id);

    /**
     * Delete the "id" cityWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
