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
import ro.west.repository.DistrictWestRepository;
import ro.west.service.DistrictWestService;
import ro.west.service.dto.DistrictWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.DistrictWest}.
 */
@RestController
@RequestMapping("/api")
public class DistrictWestResource {

    private final Logger log = LoggerFactory.getLogger(DistrictWestResource.class);

    private static final String ENTITY_NAME = "districtWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictWestService districtWestService;

    private final DistrictWestRepository districtWestRepository;

    public DistrictWestResource(DistrictWestService districtWestService, DistrictWestRepository districtWestRepository) {
        this.districtWestService = districtWestService;
        this.districtWestRepository = districtWestRepository;
    }

    /**
     * {@code POST  /district-wests} : Create a new districtWest.
     *
     * @param districtWestDTO the districtWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtWestDTO, or with status {@code 400 (Bad Request)} if the districtWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/district-wests")
    public ResponseEntity<DistrictWestDTO> createDistrictWest(@RequestBody DistrictWestDTO districtWestDTO) throws URISyntaxException {
        log.debug("REST request to save DistrictWest : {}", districtWestDTO);
        if (districtWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new districtWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictWestDTO result = districtWestService.save(districtWestDTO);
        return ResponseEntity
            .created(new URI("/api/district-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /district-wests/:id} : Updates an existing districtWest.
     *
     * @param id the id of the districtWestDTO to save.
     * @param districtWestDTO the districtWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtWestDTO,
     * or with status {@code 400 (Bad Request)} if the districtWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/district-wests/{id}")
    public ResponseEntity<DistrictWestDTO> updateDistrictWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistrictWestDTO districtWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DistrictWest : {}, {}", id, districtWestDTO);
        if (districtWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districtWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DistrictWestDTO result = districtWestService.update(districtWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, districtWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /district-wests/:id} : Partial updates given fields of an existing districtWest, field will ignore if it is null
     *
     * @param id the id of the districtWestDTO to save.
     * @param districtWestDTO the districtWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtWestDTO,
     * or with status {@code 400 (Bad Request)} if the districtWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the districtWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the districtWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/district-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DistrictWestDTO> partialUpdateDistrictWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistrictWestDTO districtWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DistrictWest partially : {}, {}", id, districtWestDTO);
        if (districtWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districtWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DistrictWestDTO> result = districtWestService.partialUpdate(districtWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, districtWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /district-wests} : get all the districtWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districtWests in body.
     */
    @GetMapping("/district-wests")
    public ResponseEntity<List<DistrictWestDTO>> getAllDistrictWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DistrictWests");
        Page<DistrictWestDTO> page = districtWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /district-wests/:id} : get the "id" districtWest.
     *
     * @param id the id of the districtWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/district-wests/{id}")
    public ResponseEntity<DistrictWestDTO> getDistrictWest(@PathVariable Long id) {
        log.debug("REST request to get DistrictWest : {}", id);
        Optional<DistrictWestDTO> districtWestDTO = districtWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtWestDTO);
    }

    /**
     * {@code DELETE  /district-wests/:id} : delete the "id" districtWest.
     *
     * @param id the id of the districtWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/district-wests/{id}")
    public ResponseEntity<Void> deleteDistrictWest(@PathVariable Long id) {
        log.debug("REST request to delete DistrictWest : {}", id);
        districtWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
