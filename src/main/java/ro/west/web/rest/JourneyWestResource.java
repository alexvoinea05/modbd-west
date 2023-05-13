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
import ro.west.repository.JourneyWestRepository;
import ro.west.service.JourneyWestService;
import ro.west.service.dto.JourneyWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.JourneyWest}.
 */
@RestController
@RequestMapping("/api")
public class JourneyWestResource {

    private final Logger log = LoggerFactory.getLogger(JourneyWestResource.class);

    private static final String ENTITY_NAME = "journeyWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneyWestService journeyWestService;

    private final JourneyWestRepository journeyWestRepository;

    public JourneyWestResource(JourneyWestService journeyWestService, JourneyWestRepository journeyWestRepository) {
        this.journeyWestService = journeyWestService;
        this.journeyWestRepository = journeyWestRepository;
    }

    /**
     * {@code POST  /journey-wests} : Create a new journeyWest.
     *
     * @param journeyWestDTO the journeyWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journeyWestDTO, or with status {@code 400 (Bad Request)} if the journeyWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journey-wests")
    public ResponseEntity<JourneyWestDTO> createJourneyWest(@RequestBody JourneyWestDTO journeyWestDTO) throws URISyntaxException {
        log.debug("REST request to save JourneyWest : {}", journeyWestDTO);
        if (journeyWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new journeyWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneyWestDTO result = journeyWestService.save(journeyWestDTO);
        return ResponseEntity
            .created(new URI("/api/journey-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journey-wests/:id} : Updates an existing journeyWest.
     *
     * @param id the id of the journeyWestDTO to save.
     * @param journeyWestDTO the journeyWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyWestDTO,
     * or with status {@code 400 (Bad Request)} if the journeyWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journeyWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journey-wests/{id}")
    public ResponseEntity<JourneyWestDTO> updateJourneyWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyWestDTO journeyWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JourneyWest : {}, {}", id, journeyWestDTO);
        if (journeyWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JourneyWestDTO result = journeyWestService.update(journeyWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /journey-wests/:id} : Partial updates given fields of an existing journeyWest, field will ignore if it is null
     *
     * @param id the id of the journeyWestDTO to save.
     * @param journeyWestDTO the journeyWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyWestDTO,
     * or with status {@code 400 (Bad Request)} if the journeyWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the journeyWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the journeyWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/journey-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JourneyWestDTO> partialUpdateJourneyWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyWestDTO journeyWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JourneyWest partially : {}, {}", id, journeyWestDTO);
        if (journeyWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JourneyWestDTO> result = journeyWestService.partialUpdate(journeyWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /journey-wests} : get all the journeyWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journeyWests in body.
     */
    @GetMapping("/journey-wests")
    public ResponseEntity<List<JourneyWestDTO>> getAllJourneyWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JourneyWests");
        Page<JourneyWestDTO> page = journeyWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /journey-wests/:id} : get the "id" journeyWest.
     *
     * @param id the id of the journeyWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journeyWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journey-wests/{id}")
    public ResponseEntity<JourneyWestDTO> getJourneyWest(@PathVariable Long id) {
        log.debug("REST request to get JourneyWest : {}", id);
        Optional<JourneyWestDTO> journeyWestDTO = journeyWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journeyWestDTO);
    }

    /**
     * {@code DELETE  /journey-wests/:id} : delete the "id" journeyWest.
     *
     * @param id the id of the journeyWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journey-wests/{id}")
    public ResponseEntity<Void> deleteJourneyWest(@PathVariable Long id) {
        log.debug("REST request to delete JourneyWest : {}", id);
        journeyWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
