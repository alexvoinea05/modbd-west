package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.RailwayStationWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.RailwayStationWest}.
 */
public interface RailwayStationWestService {
    /**
     * Save a railwayStationWest.
     *
     * @param railwayStationWestDTO the entity to save.
     * @return the persisted entity.
     */
    RailwayStationWestDTO save(RailwayStationWestDTO railwayStationWestDTO);

    /**
     * Updates a railwayStationWest.
     *
     * @param railwayStationWestDTO the entity to update.
     * @return the persisted entity.
     */
    RailwayStationWestDTO update(RailwayStationWestDTO railwayStationWestDTO);

    /**
     * Partially updates a railwayStationWest.
     *
     * @param railwayStationWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RailwayStationWestDTO> partialUpdate(RailwayStationWestDTO railwayStationWestDTO);

    /**
     * Get all the railwayStationWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RailwayStationWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" railwayStationWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RailwayStationWestDTO> findOne(Long id);

    /**
     * Delete the "id" railwayStationWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
