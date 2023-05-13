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
import ro.west.repository.CityWestRepository;
import ro.west.service.CityWestService;
import ro.west.service.dto.CityWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.CityWest}.
 */
@RestController
@RequestMapping("/api")
public class CityWestResource {

    private final Logger log = LoggerFactory.getLogger(CityWestResource.class);

    private static final String ENTITY_NAME = "cityWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityWestService cityWestService;

    private final CityWestRepository cityWestRepository;

    public CityWestResource(CityWestService cityWestService, CityWestRepository cityWestRepository) {
        this.cityWestService = cityWestService;
        this.cityWestRepository = cityWestRepository;
    }

    /**
     * {@code POST  /city-wests} : Create a new cityWest.
     *
     * @param cityWestDTO the cityWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityWestDTO, or with status {@code 400 (Bad Request)} if the cityWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-wests")
    public ResponseEntity<CityWestDTO> createCityWest(@RequestBody CityWestDTO cityWestDTO) throws URISyntaxException {
        log.debug("REST request to save CityWest : {}", cityWestDTO);
        if (cityWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new cityWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityWestDTO result = cityWestService.save(cityWestDTO);
        return ResponseEntity
            .created(new URI("/api/city-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /city-wests/:id} : Updates an existing cityWest.
     *
     * @param id the id of the cityWestDTO to save.
     * @param cityWestDTO the cityWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityWestDTO,
     * or with status {@code 400 (Bad Request)} if the cityWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-wests/{id}")
    public ResponseEntity<CityWestDTO> updateCityWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CityWestDTO cityWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CityWest : {}, {}", id, cityWestDTO);
        if (cityWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CityWestDTO result = cityWestService.update(cityWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cityWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /city-wests/:id} : Partial updates given fields of an existing cityWest, field will ignore if it is null
     *
     * @param id the id of the cityWestDTO to save.
     * @param cityWestDTO the cityWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityWestDTO,
     * or with status {@code 400 (Bad Request)} if the cityWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cityWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cityWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/city-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CityWestDTO> partialUpdateCityWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CityWestDTO cityWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CityWest partially : {}, {}", id, cityWestDTO);
        if (cityWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CityWestDTO> result = cityWestService.partialUpdate(cityWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cityWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /city-wests} : get all the cityWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cityWests in body.
     */
    @GetMapping("/city-wests")
    public ResponseEntity<List<CityWestDTO>> getAllCityWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CityWests");
        Page<CityWestDTO> page = cityWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /city-wests/:id} : get the "id" cityWest.
     *
     * @param id the id of the cityWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-wests/{id}")
    public ResponseEntity<CityWestDTO> getCityWest(@PathVariable Long id) {
        log.debug("REST request to get CityWest : {}", id);
        Optional<CityWestDTO> cityWestDTO = cityWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cityWestDTO);
    }

    /**
     * {@code DELETE  /city-wests/:id} : delete the "id" cityWest.
     *
     * @param id the id of the cityWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-wests/{id}")
    public ResponseEntity<Void> deleteCityWest(@PathVariable Long id) {
        log.debug("REST request to delete CityWest : {}", id);
        cityWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
