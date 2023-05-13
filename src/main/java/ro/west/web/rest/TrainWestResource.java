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
import ro.west.repository.TrainWestRepository;
import ro.west.service.TrainWestService;
import ro.west.service.dto.TrainWestDTO;
import ro.west.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.west.domain.TrainWest}.
 */
@RestController
@RequestMapping("/api")
public class TrainWestResource {

    private final Logger log = LoggerFactory.getLogger(TrainWestResource.class);

    private static final String ENTITY_NAME = "trainWest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainWestService trainWestService;

    private final TrainWestRepository trainWestRepository;

    public TrainWestResource(TrainWestService trainWestService, TrainWestRepository trainWestRepository) {
        this.trainWestService = trainWestService;
        this.trainWestRepository = trainWestRepository;
    }

    /**
     * {@code POST  /train-wests} : Create a new trainWest.
     *
     * @param trainWestDTO the trainWestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainWestDTO, or with status {@code 400 (Bad Request)} if the trainWest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/train-wests")
    public ResponseEntity<TrainWestDTO> createTrainWest(@RequestBody TrainWestDTO trainWestDTO) throws URISyntaxException {
        log.debug("REST request to save TrainWest : {}", trainWestDTO);
        if (trainWestDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainWest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainWestDTO result = trainWestService.save(trainWestDTO);
        return ResponseEntity
            .created(new URI("/api/train-wests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /train-wests/:id} : Updates an existing trainWest.
     *
     * @param id the id of the trainWestDTO to save.
     * @param trainWestDTO the trainWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainWestDTO,
     * or with status {@code 400 (Bad Request)} if the trainWestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/train-wests/{id}")
    public ResponseEntity<TrainWestDTO> updateTrainWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainWestDTO trainWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainWest : {}, {}", id, trainWestDTO);
        if (trainWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainWestDTO result = trainWestService.update(trainWestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainWestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /train-wests/:id} : Partial updates given fields of an existing trainWest, field will ignore if it is null
     *
     * @param id the id of the trainWestDTO to save.
     * @param trainWestDTO the trainWestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainWestDTO,
     * or with status {@code 400 (Bad Request)} if the trainWestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainWestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainWestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/train-wests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainWestDTO> partialUpdateTrainWest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainWestDTO trainWestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainWest partially : {}, {}", id, trainWestDTO);
        if (trainWestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainWestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainWestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainWestDTO> result = trainWestService.partialUpdate(trainWestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainWestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /train-wests} : get all the trainWests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainWests in body.
     */
    @GetMapping("/train-wests")
    public ResponseEntity<List<TrainWestDTO>> getAllTrainWests(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TrainWests");
        Page<TrainWestDTO> page = trainWestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /train-wests/:id} : get the "id" trainWest.
     *
     * @param id the id of the trainWestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainWestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/train-wests/{id}")
    public ResponseEntity<TrainWestDTO> getTrainWest(@PathVariable Long id) {
        log.debug("REST request to get TrainWest : {}", id);
        Optional<TrainWestDTO> trainWestDTO = trainWestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainWestDTO);
    }

    /**
     * {@code DELETE  /train-wests/:id} : delete the "id" trainWest.
     *
     * @param id the id of the trainWestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/train-wests/{id}")
    public ResponseEntity<Void> deleteTrainWest(@PathVariable Long id) {
        log.debug("REST request to delete TrainWest : {}", id);
        trainWestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
