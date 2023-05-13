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
import ro.west.repository.JourneyStatusWestRepository;
import ro.west.service.JourneyStatusWestService;
import ro.west.service.dto.JourneyStatusWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.JourneyStatusWest}.
 */
@RestController
@RequestMapping("/api")
public class JourneyStatusWestResource {

    private final Logger log = LoggerFactory.getLogger(JourneyStatusWestResource.class);

    private static final String ENTITY_NAME = "journeyStatusWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneyStatusWestService journeyStatusWestService;

    private final JourneyStatusWestRepository journeyStatusWestRepository;

    public JourneyStatusWestResource(
        JourneyStatusWestService journeyStatusWestService,
        JourneyStatusWestRepository journeyStatusWestRepository
    ) {
        this.journeyStatusWestService = journeyStatusWestService;
        this.journeyStatusWestRepository = journeyStatusWestRepository;
    }

    /**
     * {@code POST  /journey-status-wests} : Create a new journeyStatusWest.
     *
     * @param journeyStatusWestDTO the journeyStatusWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journeyStatusWestDTO, or with status {@code 400 (Bad Request)} if the journeyStatusWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journey-status-wests")
    public ResponseEntity<JourneyStatusWestDTO> createJourneyStatusWest(@RequestBody JourneyStatusWestDTO journeyStatusWestDTO)
        throws URISyntaxException {
        log.debug("REST request to save JourneyStatusWest : {}", journeyStatusWestDTO);
        if (journeyStatusWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new journeyStatusWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneyStatusWestDTO result = journeyStatusWestService.save(journeyStatusWestDTO);
        return ResponseEntity
            .created(new URI("/api/journey-status-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journey-status-wests/:id} : Updates an existing journeyStatusWest.
     *
     * @param id the id of the journeyStatusWestDTO to save.
     * @param journeyStatusWestDTO the journeyStatusWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyStatusWestDTO,
     * or with status {@code 400 (Bad Request)} if the journeyStatusWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journeyStatusWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journey-status-wests/{id}")
    public ResponseEntity<JourneyStatusWestDTO> updateJourneyStatusWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyStatusWestDTO journeyStatusWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JourneyStatusWest : {}, {}", id, journeyStatusWestDTO);
        if (journeyStatusWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyStatusWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyStatusWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JourneyStatusWestDTO result = journeyStatusWestService.update(journeyStatusWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyStatusWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /journey-status-wests/:id} : Partial updates given fields of an existing journeyStatusWest, field will ignore if it is null
     *
     * @param id the id of the journeyStatusWestDTO to save.
     * @param journeyStatusWestDTO the journeyStatusWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyStatusWestDTO,
     * or with status {@code 400 (Bad Request)} if the journeyStatusWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the journeyStatusWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the journeyStatusWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/journey-status-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JourneyStatusWestDTO> partialUpdateJourneyStatusWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyStatusWestDTO journeyStatusWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JourneyStatusWest partially : {}, {}", id, journeyStatusWestDTO);
        if (journeyStatusWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyStatusWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyStatusWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JourneyStatusWestDTO> result = journeyStatusWestService.partialUpdate(journeyStatusWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyStatusWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /journey-status-wests} : get all the journeyStatusWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journeyStatusWests in body.
     */
    @GetMapping("/journey-status-wests")
    public ResponseEntity<List<JourneyStatusWestDTO>> getAllJourneyStatusWests(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of JourneyStatusWests");
        Page<JourneyStatusWestDTO> page = journeyStatusWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /journey-status-wests/:id} : get the "id" journeyStatusWest.
     *
     * @param id the id of the journeyStatusWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journeyStatusWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journey-status-wests/{id}")
    public ResponseEntity<JourneyStatusWestDTO> getJourneyStatusWest(@PathVariable Long id) {
        log.debug("REST request to get JourneyStatusWest : {}", id);
        Optional<JourneyStatusWestDTO> journeyStatusWestDTO = journeyStatusWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journeyStatusWestDTO);
    }

    /**
     * {@code DELETE  /journey-status-wests/:id} : delete the "id" journeyStatusWest.
     *
     * @param id the id of the journeyStatusWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journey-status-wests/{id}")
    public ResponseEntity<Void> deleteJourneyStatusWest(@PathVariable Long id) {
        log.debug("REST request to delete JourneyStatusWest : {}", id);
        journeyStatusWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
