package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.AppUserWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.AppUserWest}.
 */
public interface AppUserWestService {
    /**
     * Save a appUserWest.
     *
     * @param appUserWestDTO the entity to save.
     * @return the persisted entity.
     */
    AppUserWestDTO save(AppUserWestDTO appUserWestDTO);

    /**
     * Updates a appUserWest.
     *
     * @param appUserWestDTO the entity to update.
     * @return the persisted entity.
     */
    AppUserWestDTO update(AppUserWestDTO appUserWestDTO);

    /**
     * Partially updates a appUserWest.
     *
     * @param appUserWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppUserWestDTO> partialUpdate(AppUserWestDTO appUserWestDTO);

    /**
     * Get all the appUserWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppUserWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" appUserWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppUserWestDTO> findOne(Long id);

    /**
     * Delete the "id" appUserWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
