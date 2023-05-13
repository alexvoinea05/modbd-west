package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.DistrictWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.DistrictWest}.
 */
public interface DistrictWestService {
    /**
     * Save a districtWest.
     *
     * @param districtWestDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictWestDTO save(DistrictWestDTO districtWestDTO);

    /**
     * Updates a districtWest.
     *
     * @param districtWestDTO the entity to update.
     * @return the persisted entity.
     */
    DistrictWestDTO update(DistrictWestDTO districtWestDTO);

    /**
     * Partially updates a districtWest.
     *
     * @param districtWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DistrictWestDTO> partialUpdate(DistrictWestDTO districtWestDTO);

    /**
     * Get all the districtWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistrictWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" districtWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictWestDTO> findOne(Long id);

    /**
     * Delete the "id" districtWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
