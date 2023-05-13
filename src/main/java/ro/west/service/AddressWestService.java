package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.AddressWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.AddressWest}.
 */
public interface AddressWestService {
    /**
     * Save a addressWest.
     *
     * @param addressWestDTO the entity to save.
     * @return the persisted entity.
     */
    AddressWestDTO save(AddressWestDTO addressWestDTO);

    /**
     * Updates a addressWest.
     *
     * @param addressWestDTO the entity to update.
     * @return the persisted entity.
     */
    AddressWestDTO update(AddressWestDTO addressWestDTO);

    /**
     * Partially updates a addressWest.
     *
     * @param addressWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AddressWestDTO> partialUpdate(AddressWestDTO addressWestDTO);

    /**
     * Get all the addressWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AddressWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" addressWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressWestDTO> findOne(Long id);

    /**
     * Delete the "id" addressWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
