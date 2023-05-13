package ro.west.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.west.repository.TicketWestRepository;
import ro.west.service.TicketWestService;
import ro.west.service.dto.TicketWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.TicketWest}.
 */
@RestController
@RequestMapping("/api")
public class TicketWestResource {

    private final Logger log = LoggerFactory.getLogger(TicketWestResource.class);

    private static final String ENTITY_NAME = "ticketWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketWestService ticketWestService;

    private final TicketWestRepository ticketWestRepository;

    public TicketWestResource(TicketWestService ticketWestService, TicketWestRepository ticketWestRepository) {
        this.ticketWestService = ticketWestService;
        this.ticketWestRepository = ticketWestRepository;
    }

    /**
     * {@code POST  /ticket-wests} : Create a new ticketWest.
     *
     * @param ticketWestDTO the ticketWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketWestDTO, or with status {@code 400 (Bad Request)} if the ticketWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-wests")
    public ResponseEntity<TicketWestDTO> createTicketWest(@RequestBody TicketWestDTO ticketWestDTO) throws URISyntaxException {
        log.debug("REST request to save TicketWest : {}", ticketWestDTO);
        if (ticketWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketWestDTO result = ticketWestService.save(ticketWestDTO);
        return ResponseEntity
            .created(new URI("/api/ticket-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-wests/:id} : Updates an existing ticketWest.
     *
     * @param id the id of the ticketWestDTO to save.
     * @param ticketWestDTO the ticketWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketWestDTO,
     * or with status {@code 400 (Bad Request)} if the ticketWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-wests/{id}")
    public ResponseEntity<TicketWestDTO> updateTicketWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TicketWestDTO ticketWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TicketWest : {}, {}", id, ticketWestDTO);
        if (ticketWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TicketWestDTO result = ticketWestService.update(ticketWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ticket-wests/:id} : Partial updates given fields of an existing ticketWest, field will ignore if it is null
     *
     * @param id the id of the ticketWestDTO to save.
     * @param ticketWestDTO the ticketWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketWestDTO,
     * or with status {@code 400 (Bad Request)} if the ticketWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ticketWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ticketWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ticket-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TicketWestDTO> partialUpdateTicketWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TicketWestDTO ticketWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TicketWest partially : {}, {}", id, ticketWestDTO);
        if (ticketWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TicketWestDTO> result = ticketWestService.partialUpdate(ticketWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ticket-wests} : get all the ticketWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketWests in body.
     */
    @GetMapping("/ticket-wests")
    public ResponseEntity<List<TicketWestDTO>> getAllTicketWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TicketWests");
        Page<TicketWestDTO> page = ticketWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ticket-wests/:id} : get the "id" ticketWest.
     *
     * @param id the id of the ticketWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-wests/{id}")
    public ResponseEntity<TicketWestDTO> getTicketWest(@PathVariable Long id) {
        log.debug("REST request to get TicketWest : {}", id);
        Optional<TicketWestDTO> ticketWestDTO = ticketWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketWestDTO);
    }

    /**
     * {@code DELETE  /ticket-wests/:id} : delete the "id" ticketWest.
     *
     * @param id the id of the ticketWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-wests/{id}")
    public ResponseEntity<Void> deleteTicketWest(@PathVariable Long id) {
        log.debug("REST request to delete TicketWest : {}", id);
        ticketWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
