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
import ro.west.repository.FuelTypeWestRepository;
import ro.west.service.FuelTypeWestService;
import ro.west.service.dto.FuelTypeWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.FuelTypeWest}.
 */
@RestController
@RequestMapping("/api")
public class FuelTypeWestResource {

    private final Logger log = LoggerFactory.getLogger(FuelTypeWestResource.class);

    private static final String ENTITY_NAME = "fuelTypeWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTypeWestService fuelTypeWestService;

    private final FuelTypeWestRepository fuelTypeWestRepository;

    public FuelTypeWestResource(FuelTypeWestService fuelTypeWestService, FuelTypeWestRepository fuelTypeWestRepository) {
        this.fuelTypeWestService = fuelTypeWestService;
        this.fuelTypeWestRepository = fuelTypeWestRepository;
    }

    /**
     * {@code POST  /fuel-type-wests} : Create a new fuelTypeWest.
     *
     * @param fuelTypeWestDTO the fuelTypeWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelTypeWestDTO, or with status {@code 400 (Bad Request)} if the fuelTypeWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuel-type-wests")
    public ResponseEntity<FuelTypeWestDTO> createFuelTypeWest(@RequestBody FuelTypeWestDTO fuelTypeWestDTO) throws URISyntaxException {
        log.debug("REST request to save FuelTypeWest : {}", fuelTypeWestDTO);
        if (fuelTypeWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new fuelTypeWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuelTypeWestDTO result = fuelTypeWestService.save(fuelTypeWestDTO);
        return ResponseEntity
            .created(new URI("/api/fuel-type-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuel-type-wests/:id} : Updates an existing fuelTypeWest.
     *
     * @param id the id of the fuelTypeWestDTO to save.
     * @param fuelTypeWestDTO the fuelTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuel-type-wests/{id}")
    public ResponseEntity<FuelTypeWestDTO> updateFuelTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTypeWestDTO fuelTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FuelTypeWest : {}, {}", id, fuelTypeWestDTO);
        if (fuelTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuelTypeWestDTO result = fuelTypeWestService.update(fuelTypeWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelTypeWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fuel-type-wests/:id} : Partial updates given fields of an existing fuelTypeWest, field will ignore if it is null
     *
     * @param id the id of the fuelTypeWestDTO to save.
     * @param fuelTypeWestDTO the fuelTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fuelTypeWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fuel-type-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelTypeWestDTO> partialUpdateFuelTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTypeWestDTO fuelTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuelTypeWest partially : {}, {}", id, fuelTypeWestDTO);
        if (fuelTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelTypeWestDTO> result = fuelTypeWestService.partialUpdate(fuelTypeWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelTypeWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-type-wests} : get all the fuelTypeWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTypeWests in body.
     */
    @GetMapping("/fuel-type-wests")
    public ResponseEntity<List<FuelTypeWestDTO>> getAllFuelTypeWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FuelTypeWests");
        Page<FuelTypeWestDTO> page = fuelTypeWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-type-wests/:id} : get the "id" fuelTypeWest.
     *
     * @param id the id of the fuelTypeWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelTypeWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuel-type-wests/{id}")
    public ResponseEntity<FuelTypeWestDTO> getFuelTypeWest(@PathVariable Long id) {
        log.debug("REST request to get FuelTypeWest : {}", id);
        Optional<FuelTypeWestDTO> fuelTypeWestDTO = fuelTypeWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelTypeWestDTO);
    }

    /**
     * {@code DELETE  /fuel-type-wests/:id} : delete the "id" fuelTypeWest.
     *
     * @param id the id of the fuelTypeWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuel-type-wests/{id}")
    public ResponseEntity<Void> deleteFuelTypeWest(@PathVariable Long id) {
        log.debug("REST request to delete FuelTypeWest : {}", id);
        fuelTypeWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
