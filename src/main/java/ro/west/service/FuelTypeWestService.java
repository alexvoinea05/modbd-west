package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.FuelTypeWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.FuelTypeWest}.
 */
public interface FuelTypeWestService {
    /**
     * Save a fuelTypeWest.
     *
     * @param fuelTypeWestDTO the entity to save.
     * @return the persisted entity.
     */
    FuelTypeWestDTO save(FuelTypeWestDTO fuelTypeWestDTO);

    /**
     * Updates a fuelTypeWest.
     *
     * @param fuelTypeWestDTO the entity to update.
     * @return the persisted entity.
     */
    FuelTypeWestDTO update(FuelTypeWestDTO fuelTypeWestDTO);

    /**
     * Partially updates a fuelTypeWest.
     *
     * @param fuelTypeWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelTypeWestDTO> partialUpdate(FuelTypeWestDTO fuelTypeWestDTO);

    /**
     * Get all the fuelTypeWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelTypeWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fuelTypeWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelTypeWestDTO> findOne(Long id);

    /**
     * Delete the "id" fuelTypeWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
