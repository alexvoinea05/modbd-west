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
import ro.west.repository.UserTypeWestRepository;
import ro.west.service.UserTypeWestService;
import ro.west.service.dto.UserTypeWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.UserTypeWest}.
 */
@RestController
@RequestMapping("/api")
public class UserTypeWestResource {

    private final Logger log = LoggerFactory.getLogger(UserTypeWestResource.class);

    private static final String ENTITY_NAME = "userTypeWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserTypeWestService userTypeWestService;

    private final UserTypeWestRepository userTypeWestRepository;

    public UserTypeWestResource(UserTypeWestService userTypeWestService, UserTypeWestRepository userTypeWestRepository) {
        this.userTypeWestService = userTypeWestService;
        this.userTypeWestRepository = userTypeWestRepository;
    }

    /**
     * {@code POST  /user-type-wests} : Create a new userTypeWest.
     *
     * @param userTypeWestDTO the userTypeWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userTypeWestDTO, or with status {@code 400 (Bad Request)} if the userTypeWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-type-wests")
    public ResponseEntity<UserTypeWestDTO> createUserTypeWest(@RequestBody UserTypeWestDTO userTypeWestDTO) throws URISyntaxException {
        log.debug("REST request to save UserTypeWest : {}", userTypeWestDTO);
        if (userTypeWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new userTypeWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTypeWestDTO result = userTypeWestService.save(userTypeWestDTO);
        return ResponseEntity
            .created(new URI("/api/user-type-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-type-wests/:id} : Updates an existing userTypeWest.
     *
     * @param id the id of the userTypeWestDTO to save.
     * @param userTypeWestDTO the userTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the userTypeWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-type-wests/{id}")
    public ResponseEntity<UserTypeWestDTO> updateUserTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTypeWestDTO userTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserTypeWest : {}, {}", id, userTypeWestDTO);
        if (userTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserTypeWestDTO result = userTypeWestService.update(userTypeWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTypeWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-type-wests/:id} : Partial updates given fields of an existing userTypeWest, field will ignore if it is null
     *
     * @param id the id of the userTypeWestDTO to save.
     * @param userTypeWestDTO the userTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the userTypeWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userTypeWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-type-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserTypeWestDTO> partialUpdateUserTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTypeWestDTO userTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserTypeWest partially : {}, {}", id, userTypeWestDTO);
        if (userTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserTypeWestDTO> result = userTypeWestService.partialUpdate(userTypeWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTypeWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-type-wests} : get all the userTypeWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userTypeWests in body.
     */
    @GetMapping("/user-type-wests")
    public ResponseEntity<List<UserTypeWestDTO>> getAllUserTypeWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserTypeWests");
        Page<UserTypeWestDTO> page = userTypeWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-type-wests/:id} : get the "id" userTypeWest.
     *
     * @param id the id of the userTypeWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userTypeWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-type-wests/{id}")
    public ResponseEntity<UserTypeWestDTO> getUserTypeWest(@PathVariable Long id) {
        log.debug("REST request to get UserTypeWest : {}", id);
        Optional<UserTypeWestDTO> userTypeWestDTO = userTypeWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTypeWestDTO);
    }

    /**
     * {@code DELETE  /user-type-wests/:id} : delete the "id" userTypeWest.
     *
     * @param id the id of the userTypeWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-type-wests/{id}")
    public ResponseEntity<Void> deleteUserTypeWest(@PathVariable Long id) {
        log.debug("REST request to delete UserTypeWest : {}", id);
        userTypeWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
