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
import ro.west.repository.RailwayStationWestRepository;
import ro.west.service.RailwayStationWestService;
import ro.west.service.dto.RailwayStationWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.RailwayStationWest}.
 */
@RestController
@RequestMapping("/api")
public class RailwayStationWestResource {

    private final Logger log = LoggerFactory.getLogger(RailwayStationWestResource.class);

    private static final String ENTITY_NAME = "railwayStationWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RailwayStationWestService railwayStationWestService;

    private final RailwayStationWestRepository railwayStationWestRepository;

    public RailwayStationWestResource(
        RailwayStationWestService railwayStationWestService,
        RailwayStationWestRepository railwayStationWestRepository
    ) {
        this.railwayStationWestService = railwayStationWestService;
        this.railwayStationWestRepository = railwayStationWestRepository;
    }

    /**
     * {@code POST  /railway-station-wests} : Create a new railwayStationWest.
     *
     * @param railwayStationWestDTO the railwayStationWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new railwayStationWestDTO, or with status {@code 400 (Bad Request)} if the railwayStationWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/railway-station-wests")
    public ResponseEntity<RailwayStationWestDTO> createRailwayStationWest(@RequestBody RailwayStationWestDTO railwayStationWestDTO)
        throws URISyntaxException {
        log.debug("REST request to save RailwayStationWest : {}", railwayStationWestDTO);
        if (railwayStationWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new railwayStationWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RailwayStationWestDTO result = railwayStationWestService.save(railwayStationWestDTO);
        return ResponseEntity
            .created(new URI("/api/railway-station-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /railway-station-wests/:id} : Updates an existing railwayStationWest.
     *
     * @param id the id of the railwayStationWestDTO to save.
     * @param railwayStationWestDTO the railwayStationWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayStationWestDTO,
     * or with status {@code 400 (Bad Request)} if the railwayStationWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the railwayStationWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/railway-station-wests/{id}")
    public ResponseEntity<RailwayStationWestDTO> updateRailwayStationWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayStationWestDTO railwayStationWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RailwayStationWest : {}, {}", id, railwayStationWestDTO);
        if (railwayStationWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayStationWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayStationWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RailwayStationWestDTO result = railwayStationWestService.update(railwayStationWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayStationWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /railway-station-wests/:id} : Partial updates given fields of an existing railwayStationWest, field will ignore if it is null
     *
     * @param id the id of the railwayStationWestDTO to save.
     * @param railwayStationWestDTO the railwayStationWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayStationWestDTO,
     * or with status {@code 400 (Bad Request)} if the railwayStationWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the railwayStationWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the railwayStationWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/railway-station-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RailwayStationWestDTO> partialUpdateRailwayStationWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayStationWestDTO railwayStationWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RailwayStationWest partially : {}, {}", id, railwayStationWestDTO);
        if (railwayStationWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayStationWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayStationWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RailwayStationWestDTO> result = railwayStationWestService.partialUpdate(railwayStationWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayStationWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /railway-station-wests} : get all the railwayStationWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of railwayStationWests in body.
     */
    @GetMapping("/railway-station-wests")
    public ResponseEntity<List<RailwayStationWestDTO>> getAllRailwayStationWests(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RailwayStationWests");
        Page<RailwayStationWestDTO> page = railwayStationWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /railway-station-wests/:id} : get the "id" railwayStationWest.
     *
     * @param id the id of the railwayStationWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the railwayStationWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/railway-station-wests/{id}")
    public ResponseEntity<RailwayStationWestDTO> getRailwayStationWest(@PathVariable Long id) {
        log.debug("REST request to get RailwayStationWest : {}", id);
        Optional<RailwayStationWestDTO> railwayStationWestDTO = railwayStationWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(railwayStationWestDTO);
    }

    /**
     * {@code DELETE  /railway-station-wests/:id} : delete the "id" railwayStationWest.
     *
     * @param id the id of the railwayStationWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/railway-station-wests/{id}")
    public ResponseEntity<Void> deleteRailwayStationWest(@PathVariable Long id) {
        log.debug("REST request to delete RailwayStationWest : {}", id);
        railwayStationWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
