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
import ro.west.repository.AppUserWestRepository;
import ro.west.service.AppUserWestService;
import ro.west.service.dto.AppUserWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.AppUserWest}.
 */
@RestController
@RequestMapping("/api")
public class AppUserWestResource {

    private final Logger log = LoggerFactory.getLogger(AppUserWestResource.class);

    private static final String ENTITY_NAME = "appUserWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppUserWestService appUserWestService;

    private final AppUserWestRepository appUserWestRepository;

    public AppUserWestResource(AppUserWestService appUserWestService, AppUserWestRepository appUserWestRepository) {
        this.appUserWestService = appUserWestService;
        this.appUserWestRepository = appUserWestRepository;
    }

    /**
     * {@code POST  /app-user-wests} : Create a new appUserWest.
     *
     * @param appUserWestDTO the appUserWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appUserWestDTO, or with status {@code 400 (Bad Request)} if the appUserWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-user-wests")
    public ResponseEntity<AppUserWestDTO> createAppUserWest(@RequestBody AppUserWestDTO appUserWestDTO) throws URISyntaxException {
        log.debug("REST request to save AppUserWest : {}", appUserWestDTO);
        if (appUserWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new appUserWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppUserWestDTO result = appUserWestService.save(appUserWestDTO);
        return ResponseEntity
            .created(new URI("/api/app-user-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-user-wests/:id} : Updates an existing appUserWest.
     *
     * @param id the id of the appUserWestDTO to save.
     * @param appUserWestDTO the appUserWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserWestDTO,
     * or with status {@code 400 (Bad Request)} if the appUserWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appUserWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-user-wests/{id}")
    public ResponseEntity<AppUserWestDTO> updateAppUserWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserWestDTO appUserWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppUserWest : {}, {}", id, appUserWestDTO);
        if (appUserWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppUserWestDTO result = appUserWestService.update(appUserWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appUserWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-user-wests/:id} : Partial updates given fields of an existing appUserWest, field will ignore if it is null
     *
     * @param id the id of the appUserWestDTO to save.
     * @param appUserWestDTO the appUserWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserWestDTO,
     * or with status {@code 400 (Bad Request)} if the appUserWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appUserWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appUserWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-user-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppUserWestDTO> partialUpdateAppUserWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserWestDTO appUserWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppUserWest partially : {}, {}", id, appUserWestDTO);
        if (appUserWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppUserWestDTO> result = appUserWestService.partialUpdate(appUserWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appUserWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-user-wests} : get all the appUserWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appUserWests in body.
     */
    @GetMapping("/app-user-wests")
    public ResponseEntity<List<AppUserWestDTO>> getAllAppUserWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AppUserWests");
        Page<AppUserWestDTO> page = appUserWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-user-wests/:id} : get the "id" appUserWest.
     *
     * @param id the id of the appUserWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appUserWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-user-wests/{id}")
    public ResponseEntity<AppUserWestDTO> getAppUserWest(@PathVariable Long id) {
        log.debug("REST request to get AppUserWest : {}", id);
        Optional<AppUserWestDTO> appUserWestDTO = appUserWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appUserWestDTO);
    }

    /**
     * {@code DELETE  /app-user-wests/:id} : delete the "id" appUserWest.
     *
     * @param id the id of the appUserWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-user-wests/{id}")
    public ResponseEntity<Void> deleteAppUserWest(@PathVariable Long id) {
        log.debug("REST request to delete AppUserWest : {}", id);
        appUserWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
