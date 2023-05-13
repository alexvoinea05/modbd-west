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
import ro.west.repository.RailwayTypeWestRepository;
import ro.west.service.RailwayTypeWestService;
import ro.west.service.dto.RailwayTypeWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.RailwayTypeWest}.
 */
@RestController
@RequestMapping("/api")
public class RailwayTypeWestResource {

    private final Logger log = LoggerFactory.getLogger(RailwayTypeWestResource.class);

    private static final String ENTITY_NAME = "railwayTypeWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RailwayTypeWestService railwayTypeWestService;

    private final RailwayTypeWestRepository railwayTypeWestRepository;

    public RailwayTypeWestResource(RailwayTypeWestService railwayTypeWestService, RailwayTypeWestRepository railwayTypeWestRepository) {
        this.railwayTypeWestService = railwayTypeWestService;
        this.railwayTypeWestRepository = railwayTypeWestRepository;
    }

    /**
     * {@code POST  /railway-type-wests} : Create a new railwayTypeWest.
     *
     * @param railwayTypeWestDTO the railwayTypeWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new railwayTypeWestDTO, or with status {@code 400 (Bad Request)} if the railwayTypeWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/railway-type-wests")
    public ResponseEntity<RailwayTypeWestDTO> createRailwayTypeWest(@RequestBody RailwayTypeWestDTO railwayTypeWestDTO)
        throws URISyntaxException {
        log.debug("REST request to save RailwayTypeWest : {}", railwayTypeWestDTO);
        if (railwayTypeWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new railwayTypeWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RailwayTypeWestDTO result = railwayTypeWestService.save(railwayTypeWestDTO);
        return ResponseEntity
            .created(new URI("/api/railway-type-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /railway-type-wests/:id} : Updates an existing railwayTypeWest.
     *
     * @param id the id of the railwayTypeWestDTO to save.
     * @param railwayTypeWestDTO the railwayTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the railwayTypeWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the railwayTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/railway-type-wests/{id}")
    public ResponseEntity<RailwayTypeWestDTO> updateRailwayTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayTypeWestDTO railwayTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RailwayTypeWest : {}, {}", id, railwayTypeWestDTO);
        if (railwayTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RailwayTypeWestDTO result = railwayTypeWestService.update(railwayTypeWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayTypeWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /railway-type-wests/:id} : Partial updates given fields of an existing railwayTypeWest, field will ignore if it is null
     *
     * @param id the id of the railwayTypeWestDTO to save.
     * @param railwayTypeWestDTO the railwayTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the railwayTypeWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the railwayTypeWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the railwayTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/railway-type-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RailwayTypeWestDTO> partialUpdateRailwayTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayTypeWestDTO railwayTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RailwayTypeWest partially : {}, {}", id, railwayTypeWestDTO);
        if (railwayTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RailwayTypeWestDTO> result = railwayTypeWestService.partialUpdate(railwayTypeWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayTypeWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /railway-type-wests} : get all the railwayTypeWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of railwayTypeWests in body.
     */
    @GetMapping("/railway-type-wests")
    public ResponseEntity<List<RailwayTypeWestDTO>> getAllRailwayTypeWests(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RailwayTypeWests");
        Page<RailwayTypeWestDTO> page = railwayTypeWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /railway-type-wests/:id} : get the "id" railwayTypeWest.
     *
     * @param id the id of the railwayTypeWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the railwayTypeWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/railway-type-wests/{id}")
    public ResponseEntity<RailwayTypeWestDTO> getRailwayTypeWest(@PathVariable Long id) {
        log.debug("REST request to get RailwayTypeWest : {}", id);
        Optional<RailwayTypeWestDTO> railwayTypeWestDTO = railwayTypeWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(railwayTypeWestDTO);
    }

    /**
     * {@code DELETE  /railway-type-wests/:id} : delete the "id" railwayTypeWest.
     *
     * @param id the id of the railwayTypeWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/railway-type-wests/{id}")
    public ResponseEntity<Void> deleteRailwayTypeWest(@PathVariable Long id) {
        log.debug("REST request to delete RailwayTypeWest : {}", id);
        railwayTypeWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
