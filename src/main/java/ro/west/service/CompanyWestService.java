package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.CompanyWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.CompanyWest}.
 */
public interface CompanyWestService {
    /**
     * Save a companyWest.
     *
     * @param companyWestDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyWestDTO save(CompanyWestDTO companyWestDTO);

    /**
     * Updates a companyWest.
     *
     * @param companyWestDTO the entity to update.
     * @return the persisted entity.
     */
    CompanyWestDTO update(CompanyWestDTO companyWestDTO);

    /**
     * Partially updates a companyWest.
     *
     * @param companyWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyWestDTO> partialUpdate(CompanyWestDTO companyWestDTO);

    /**
     * Get all the companyWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" companyWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyWestDTO> findOne(Long id);

    /**
     * Delete the "id" companyWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
