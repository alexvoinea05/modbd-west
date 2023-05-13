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
import ro.west.repository.AddressWestRepository;
import ro.west.service.AddressWestService;
import ro.west.service.dto.AddressWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.AddressWest}.
 */
@RestController
@RequestMapping("/api")
public class AddressWestResource {

    private final Logger log = LoggerFactory.getLogger(AddressWestResource.class);

    private static final String ENTITY_NAME = "addressWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressWestService addressWestService;

    private final AddressWestRepository addressWestRepository;

    public AddressWestResource(AddressWestService addressWestService, AddressWestRepository addressWestRepository) {
        this.addressWestService = addressWestService;
        this.addressWestRepository = addressWestRepository;
    }

    /**
     * {@code POST  /address-wests} : Create a new addressWest.
     *
     * @param addressWestDTO the addressWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressWestDTO, or with status {@code 400 (Bad Request)} if the addressWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/address-wests")
    public ResponseEntity<AddressWestDTO> createAddressWest(@RequestBody AddressWestDTO addressWestDTO) throws URISyntaxException {
        log.debug("REST request to save AddressWest : {}", addressWestDTO);
        if (addressWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressWestDTO result = addressWestService.save(addressWestDTO);
        return ResponseEntity
            .created(new URI("/api/address-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /address-wests/:id} : Updates an existing addressWest.
     *
     * @param id the id of the addressWestDTO to save.
     * @param addressWestDTO the addressWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressWestDTO,
     * or with status {@code 400 (Bad Request)} if the addressWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/address-wests/{id}")
    public ResponseEntity<AddressWestDTO> updateAddressWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AddressWestDTO addressWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AddressWest : {}, {}", id, addressWestDTO);
        if (addressWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AddressWestDTO result = addressWestService.update(addressWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /address-wests/:id} : Partial updates given fields of an existing addressWest, field will ignore if it is null
     *
     * @param id the id of the addressWestDTO to save.
     * @param addressWestDTO the addressWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressWestDTO,
     * or with status {@code 400 (Bad Request)} if the addressWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the addressWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the addressWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/address-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AddressWestDTO> partialUpdateAddressWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AddressWestDTO addressWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AddressWest partially : {}, {}", id, addressWestDTO);
        if (addressWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddressWestDTO> result = addressWestService.partialUpdate(addressWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /address-wests} : get all the addressWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressWests in body.
     */
    @GetMapping("/address-wests")
    public ResponseEntity<List<AddressWestDTO>> getAllAddressWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AddressWests");
        Page<AddressWestDTO> page = addressWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /address-wests/:id} : get the "id" addressWest.
     *
     * @param id the id of the addressWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/address-wests/{id}")
    public ResponseEntity<AddressWestDTO> getAddressWest(@PathVariable Long id) {
        log.debug("REST request to get AddressWest : {}", id);
        Optional<AddressWestDTO> addressWestDTO = addressWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressWestDTO);
    }

    /**
     * {@code DELETE  /address-wests/:id} : delete the "id" addressWest.
     *
     * @param id the id of the addressWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/address-wests/{id}")
    public ResponseEntity<Void> deleteAddressWest(@PathVariable Long id) {
        log.debug("REST request to delete AddressWest : {}", id);
        addressWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
