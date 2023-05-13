package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.LicenseWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.LicenseWest}.
 */
public interface LicenseWestService {
    /**
     * Save a licenseWest.
     *
     * @param licenseWestDTO the entity to save.
     * @return the persisted entity.
     */
    LicenseWestDTO save(LicenseWestDTO licenseWestDTO);

    /**
     * Updates a licenseWest.
     *
     * @param licenseWestDTO the entity to update.
     * @return the persisted entity.
     */
    LicenseWestDTO update(LicenseWestDTO licenseWestDTO);

    /**
     * Partially updates a licenseWest.
     *
     * @param licenseWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LicenseWestDTO> partialUpdate(LicenseWestDTO licenseWestDTO);

    /**
     * Get all the licenseWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LicenseWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" licenseWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LicenseWestDTO> findOne(Long id);

    /**
     * Delete the "id" licenseWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
