package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.CompanyLicenseWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.CompanyLicenseWest}.
 */
public interface CompanyLicenseWestService {
    /**
     * Save a companyLicenseWest.
     *
     * @param companyLicenseWestDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyLicenseWestDTO save(CompanyLicenseWestDTO companyLicenseWestDTO);

    /**
     * Updates a companyLicenseWest.
     *
     * @param companyLicenseWestDTO the entity to update.
     * @return the persisted entity.
     */
    CompanyLicenseWestDTO update(CompanyLicenseWestDTO companyLicenseWestDTO);

    /**
     * Partially updates a companyLicenseWest.
     *
     * @param companyLicenseWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyLicenseWestDTO> partialUpdate(CompanyLicenseWestDTO companyLicenseWestDTO);

    /**
     * Get all the companyLicenseWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyLicenseWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" companyLicenseWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyLicenseWestDTO> findOne(Long id);

    /**
     * Delete the "id" companyLicenseWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
