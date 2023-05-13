package ro.west.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.west.service.dto.TicketWestDTO;

/**
 * Service Interface for managing {@link ro.west.domain.TicketWest}.
 */
public interface TicketWestService {
    /**
     * Save a ticketWest.
     *
     * @param ticketWestDTO the entity to save.
     * @return the persisted entity.
     */
    TicketWestDTO save(TicketWestDTO ticketWestDTO);

    /**
     * Updates a ticketWest.
     *
     * @param ticketWestDTO the entity to update.
     * @return the persisted entity.
     */
    TicketWestDTO update(TicketWestDTO ticketWestDTO);

    /**
     * Partially updates a ticketWest.
     *
     * @param ticketWestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TicketWestDTO> partialUpdate(TicketWestDTO ticketWestDTO);

    /**
     * Get all the ticketWests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketWestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticketWest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketWestDTO> findOne(Long id);

    /**
     * Delete the "id" ticketWest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
