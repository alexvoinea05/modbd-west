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
import ro.west.repository.LicenseWestRepository;
import ro.west.service.LicenseWestService;
import ro.west.service.dto.LicenseWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.LicenseWest}.
 */
@RestController
@RequestMapping("/api")
public class LicenseWestResource {

    private final Logger log = LoggerFactory.getLogger(LicenseWestResource.class);

    private static final String ENTITY_NAME = "licenseWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenseWestService licenseWestService;

    private final LicenseWestRepository licenseWestRepository;

    public LicenseWestResource(LicenseWestService licenseWestService, LicenseWestRepository licenseWestRepository) {
        this.licenseWestService = licenseWestService;
        this.licenseWestRepository = licenseWestRepository;
    }

    /**
     * {@code POST  /license-wests} : Create a new licenseWest.
     *
     * @param licenseWestDTO the licenseWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenseWestDTO, or with status {@code 400 (Bad Request)} if the licenseWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/license-wests")
    public ResponseEntity<LicenseWestDTO> createLicenseWest(@RequestBody LicenseWestDTO licenseWestDTO) throws URISyntaxException {
        log.debug("REST request to save LicenseWest : {}", licenseWestDTO);
        if (licenseWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new licenseWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenseWestDTO result = licenseWestService.save(licenseWestDTO);
        return ResponseEntity
            .created(new URI("/api/license-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /license-wests/:id} : Updates an existing licenseWest.
     *
     * @param id the id of the licenseWestDTO to save.
     * @param licenseWestDTO the licenseWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseWestDTO,
     * or with status {@code 400 (Bad Request)} if the licenseWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenseWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/license-wests/{id}")
    public ResponseEntity<LicenseWestDTO> updateLicenseWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LicenseWestDTO licenseWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LicenseWest : {}, {}", id, licenseWestDTO);
        if (licenseWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenseWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenseWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LicenseWestDTO result = licenseWestService.update(licenseWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /license-wests/:id} : Partial updates given fields of an existing licenseWest, field will ignore if it is null
     *
     * @param id the id of the licenseWestDTO to save.
     * @param licenseWestDTO the licenseWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseWestDTO,
     * or with status {@code 400 (Bad Request)} if the licenseWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the licenseWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the licenseWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/license-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LicenseWestDTO> partialUpdateLicenseWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LicenseWestDTO licenseWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LicenseWest partially : {}, {}", id, licenseWestDTO);
        if (licenseWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenseWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenseWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LicenseWestDTO> result = licenseWestService.partialUpdate(licenseWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /license-wests} : get all the licenseWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenseWests in body.
     */
    @GetMapping("/license-wests")
    public ResponseEntity<List<LicenseWestDTO>> getAllLicenseWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LicenseWests");
        Page<LicenseWestDTO> page = licenseWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /license-wests/:id} : get the "id" licenseWest.
     *
     * @param id the id of the licenseWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenseWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/license-wests/{id}")
    public ResponseEntity<LicenseWestDTO> getLicenseWest(@PathVariable Long id) {
        log.debug("REST request to get LicenseWest : {}", id);
        Optional<LicenseWestDTO> licenseWestDTO = licenseWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licenseWestDTO);
    }

    /**
     * {@code DELETE  /license-wests/:id} : delete the "id" licenseWest.
     *
     * @param id the id of the licenseWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/license-wests/{id}")
    public ResponseEntity<Void> deleteLicenseWest(@PathVariable Long id) {
        log.debug("REST request to delete LicenseWest : {}", id);
        licenseWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
