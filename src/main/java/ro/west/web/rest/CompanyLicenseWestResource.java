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
import ro.west.repository.CompanyLicenseWestRepository;
import ro.west.service.CompanyLicenseWestService;
import ro.west.service.dto.CompanyLicenseWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.CompanyLicenseWest}.
 */
@RestController
@RequestMapping("/api")
public class CompanyLicenseWestResource {

    private final Logger log = LoggerFactory.getLogger(CompanyLicenseWestResource.class);

    private static final String ENTITY_NAME = "companyLicenseWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyLicenseWestService companyLicenseWestService;

    private final CompanyLicenseWestRepository companyLicenseWestRepository;

    public CompanyLicenseWestResource(
        CompanyLicenseWestService companyLicenseWestService,
        CompanyLicenseWestRepository companyLicenseWestRepository
    ) {
        this.companyLicenseWestService = companyLicenseWestService;
        this.companyLicenseWestRepository = companyLicenseWestRepository;
    }

    /**
     * {@code POST  /company-license-wests} : Create a new companyLicenseWest.
     *
     * @param companyLicenseWestDTO the companyLicenseWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyLicenseWestDTO, or with status {@code 400 (Bad Request)} if the companyLicenseWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-license-wests")
    public ResponseEntity<CompanyLicenseWestDTO> createCompanyLicenseWest(@RequestBody CompanyLicenseWestDTO companyLicenseWestDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompanyLicenseWest : {}", companyLicenseWestDTO);
        if (companyLicenseWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyLicenseWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyLicenseWestDTO result = companyLicenseWestService.save(companyLicenseWestDTO);
        return ResponseEntity
            .created(new URI("/api/company-license-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-license-wests/:id} : Updates an existing companyLicenseWest.
     *
     * @param id the id of the companyLicenseWestDTO to save.
     * @param companyLicenseWestDTO the companyLicenseWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyLicenseWestDTO,
     * or with status {@code 400 (Bad Request)} if the companyLicenseWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyLicenseWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-license-wests/{id}")
    public ResponseEntity<CompanyLicenseWestDTO> updateCompanyLicenseWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyLicenseWestDTO companyLicenseWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyLicenseWest : {}, {}", id, companyLicenseWestDTO);
        if (companyLicenseWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyLicenseWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyLicenseWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyLicenseWestDTO result = companyLicenseWestService.update(companyLicenseWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyLicenseWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-license-wests/:id} : Partial updates given fields of an existing companyLicenseWest, field will ignore if it is null
     *
     * @param id the id of the companyLicenseWestDTO to save.
     * @param companyLicenseWestDTO the companyLicenseWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyLicenseWestDTO,
     * or with status {@code 400 (Bad Request)} if the companyLicenseWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyLicenseWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyLicenseWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-license-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyLicenseWestDTO> partialUpdateCompanyLicenseWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyLicenseWestDTO companyLicenseWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyLicenseWest partially : {}, {}", id, companyLicenseWestDTO);
        if (companyLicenseWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyLicenseWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyLicenseWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyLicenseWestDTO> result = companyLicenseWestService.partialUpdate(companyLicenseWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyLicenseWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-license-wests} : get all the companyLicenseWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyLicenseWests in body.
     */
    @GetMapping("/company-license-wests")
    public ResponseEntity<List<CompanyLicenseWestDTO>> getAllCompanyLicenseWests(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompanyLicenseWests");
        Page<CompanyLicenseWestDTO> page = companyLicenseWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-license-wests/:id} : get the "id" companyLicenseWest.
     *
     * @param id the id of the companyLicenseWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyLicenseWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-license-wests/{id}")
    public ResponseEntity<CompanyLicenseWestDTO> getCompanyLicenseWest(@PathVariable Long id) {
        log.debug("REST request to get CompanyLicenseWest : {}", id);
        Optional<CompanyLicenseWestDTO> companyLicenseWestDTO = companyLicenseWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyLicenseWestDTO);
    }

    /**
     * {@code DELETE  /company-license-wests/:id} : delete the "id" companyLicenseWest.
     *
     * @param id the id of the companyLicenseWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-license-wests/{id}")
    public ResponseEntity<Void> deleteCompanyLicenseWest(@PathVariable Long id) {
        log.debug("REST request to delete CompanyLicenseWest : {}", id);
        companyLicenseWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
