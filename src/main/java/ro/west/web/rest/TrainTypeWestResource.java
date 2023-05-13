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
import ro.west.repository.TrainTypeWestRepository;
import ro.west.service.TrainTypeWestService;
import ro.west.service.dto.TrainTypeWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.TrainTypeWest}.
 */
@RestController
@RequestMapping("/api")
public class TrainTypeWestResource {

    private final Logger log = LoggerFactory.getLogger(TrainTypeWestResource.class);

    private static final String ENTITY_NAME = "trainTypeWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainTypeWestService trainTypeWestService;

    private final TrainTypeWestRepository trainTypeWestRepository;

    public TrainTypeWestResource(TrainTypeWestService trainTypeWestService, TrainTypeWestRepository trainTypeWestRepository) {
        this.trainTypeWestService = trainTypeWestService;
        this.trainTypeWestRepository = trainTypeWestRepository;
    }

    /**
     * {@code POST  /train-type-wests} : Create a new trainTypeWest.
     *
     * @param trainTypeWestDTO the trainTypeWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainTypeWestDTO, or with status {@code 400 (Bad Request)} if the trainTypeWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/train-type-wests")
    public ResponseEntity<TrainTypeWestDTO> createTrainTypeWest(@RequestBody TrainTypeWestDTO trainTypeWestDTO) throws URISyntaxException {
        log.debug("REST request to save TrainTypeWest : {}", trainTypeWestDTO);
        if (trainTypeWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainTypeWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainTypeWestDTO result = trainTypeWestService.save(trainTypeWestDTO);
        return ResponseEntity
            .created(new URI("/api/train-type-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /train-type-wests/:id} : Updates an existing trainTypeWest.
     *
     * @param id the id of the trainTypeWestDTO to save.
     * @param trainTypeWestDTO the trainTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the trainTypeWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/train-type-wests/{id}")
    public ResponseEntity<TrainTypeWestDTO> updateTrainTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainTypeWestDTO trainTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainTypeWest : {}, {}", id, trainTypeWestDTO);
        if (trainTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainTypeWestDTO result = trainTypeWestService.update(trainTypeWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainTypeWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /train-type-wests/:id} : Partial updates given fields of an existing trainTypeWest, field will ignore if it is null
     *
     * @param id the id of the trainTypeWestDTO to save.
     * @param trainTypeWestDTO the trainTypeWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainTypeWestDTO,
     * or with status {@code 400 (Bad Request)} if the trainTypeWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainTypeWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainTypeWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/train-type-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainTypeWestDTO> partialUpdateTrainTypeWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainTypeWestDTO trainTypeWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainTypeWest partially : {}, {}", id, trainTypeWestDTO);
        if (trainTypeWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainTypeWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainTypeWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainTypeWestDTO> result = trainTypeWestService.partialUpdate(trainTypeWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainTypeWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /train-type-wests} : get all the trainTypeWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainTypeWests in body.
     */
    @GetMapping("/train-type-wests")
    public ResponseEntity<List<TrainTypeWestDTO>> getAllTrainTypeWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TrainTypeWests");
        Page<TrainTypeWestDTO> page = trainTypeWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /train-type-wests/:id} : get the "id" trainTypeWest.
     *
     * @param id the id of the trainTypeWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainTypeWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/train-type-wests/{id}")
    public ResponseEntity<TrainTypeWestDTO> getTrainTypeWest(@PathVariable Long id) {
        log.debug("REST request to get TrainTypeWest : {}", id);
        Optional<TrainTypeWestDTO> trainTypeWestDTO = trainTypeWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainTypeWestDTO);
    }

    /**
     * {@code DELETE  /train-type-wests/:id} : delete the "id" trainTypeWest.
     *
     * @param id the id of the trainTypeWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/train-type-wests/{id}")
    public ResponseEntity<Void> deleteTrainTypeWest(@PathVariable Long id) {
        log.debug("REST request to delete TrainTypeWest : {}", id);
        trainTypeWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
