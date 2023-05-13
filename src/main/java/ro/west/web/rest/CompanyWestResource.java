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
import ro.west.repository.CompanyWestRepository;
import ro.west.service.CompanyWestService;
import ro.west.service.dto.CompanyWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.CompanyWest}.
 */
@RestController
@RequestMapping("/api")
public class CompanyWestResource {

    private final Logger log = LoggerFactory.getLogger(CompanyWestResource.class);

    private static final String ENTITY_NAME = "companyWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyWestService companyWestService;

    private final CompanyWestRepository companyWestRepository;

    public CompanyWestResource(CompanyWestService companyWestService, CompanyWestRepository companyWestRepository) {
        this.companyWestService = companyWestService;
        this.companyWestRepository = companyWestRepository;
    }

    /**
     * {@code POST  /company-wests} : Create a new companyWest.
     *
     * @param companyWestDTO the companyWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyWestDTO, or with status {@code 400 (Bad Request)} if the companyWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-wests")
    public ResponseEntity<CompanyWestDTO> createCompanyWest(@RequestBody CompanyWestDTO companyWestDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyWest : {}", companyWestDTO);
        if (companyWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyWestDTO result = companyWestService.save(companyWestDTO);
        return ResponseEntity
            .created(new URI("/api/company-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-wests/:id} : Updates an existing companyWest.
     *
     * @param id the id of the companyWestDTO to save.
     * @param companyWestDTO the companyWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyWestDTO,
     * or with status {@code 400 (Bad Request)} if the companyWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-wests/{id}")
    public ResponseEntity<CompanyWestDTO> updateCompanyWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyWestDTO companyWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyWest : {}, {}", id, companyWestDTO);
        if (companyWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyWestDTO result = companyWestService.update(companyWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-wests/:id} : Partial updates given fields of an existing companyWest, field will ignore if it is null
     *
     * @param id the id of the companyWestDTO to save.
     * @param companyWestDTO the companyWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyWestDTO,
     * or with status {@code 400 (Bad Request)} if the companyWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyWestDTO> partialUpdateCompanyWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyWestDTO companyWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyWest partially : {}, {}", id, companyWestDTO);
        if (companyWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyWestDTO> result = companyWestService.partialUpdate(companyWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-wests} : get all the companyWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyWests in body.
     */
    @GetMapping("/company-wests")
    public ResponseEntity<List<CompanyWestDTO>> getAllCompanyWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CompanyWests");
        Page<CompanyWestDTO> page = companyWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-wests/:id} : get the "id" companyWest.
     *
     * @param id the id of the companyWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-wests/{id}")
    public ResponseEntity<CompanyWestDTO> getCompanyWest(@PathVariable Long id) {
        log.debug("REST request to get CompanyWest : {}", id);
        Optional<CompanyWestDTO> companyWestDTO = companyWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyWestDTO);
    }

    /**
     * {@code DELETE  /company-wests/:id} : delete the "id" companyWest.
     *
     * @param id the id of the companyWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-wests/{id}")
    public ResponseEntity<Void> deleteCompanyWest(@PathVariable Long id) {
        log.debug("REST request to delete CompanyWest : {}", id);
        companyWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
