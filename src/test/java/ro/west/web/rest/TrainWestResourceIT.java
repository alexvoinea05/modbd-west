package ro.west.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ro.west.IntegrationTest;
import ro.west.domain.TrainWest;
import ro.west.repository.TrainWestRepository;
import ro.west.service.dto.TrainWestDTO;
import ro.west.service.mapper.TrainWestMapper;

/**
 * Integration tests for the {@link TrainWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainWestResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_OF_SEATS = 1L;
    private static final Long UPDATED_NUMBER_OF_SEATS = 2L;

    private static final Long DEFAULT_FUEL_TYPE_ID = 1L;
    private static final Long UPDATED_FUEL_TYPE_ID = 2L;

    private static final Long DEFAULT_TRAIN_TYPE_ID = 1L;
    private static final Long UPDATED_TRAIN_TYPE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/train-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainWestRepository trainWestRepository;

    @Autowired
    private TrainWestMapper trainWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainWestMockMvc;

    private TrainWest trainWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainWest createEntity(EntityManager em) {
        TrainWest trainWest = new TrainWest()
            .code(DEFAULT_CODE)
            .numberOfSeats(DEFAULT_NUMBER_OF_SEATS)
            .fuelTypeId(DEFAULT_FUEL_TYPE_ID)
            .trainTypeId(DEFAULT_TRAIN_TYPE_ID);
        return trainWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainWest createUpdatedEntity(EntityManager em) {
        TrainWest trainWest = new TrainWest()
            .code(UPDATED_CODE)
            .numberOfSeats(UPDATED_NUMBER_OF_SEATS)
            .fuelTypeId(UPDATED_FUEL_TYPE_ID)
            .trainTypeId(UPDATED_TRAIN_TYPE_ID);
        return trainWest;
    }

    @BeforeEach
    public void initTest() {
        trainWest = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainWest() throws Exception {
        int databaseSizeBeforeCreate = trainWestRepository.findAll().size();
        // Create the TrainWest
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);
        restTrainWestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainWestDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeCreate + 1);
        TrainWest testTrainWest = trainWestList.get(trainWestList.size() - 1);
        assertThat(testTrainWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrainWest.getNumberOfSeats()).isEqualTo(DEFAULT_NUMBER_OF_SEATS);
        assertThat(testTrainWest.getFuelTypeId()).isEqualTo(DEFAULT_FUEL_TYPE_ID);
        assertThat(testTrainWest.getTrainTypeId()).isEqualTo(DEFAULT_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void createTrainWestWithExistingId() throws Exception {
        // Create the TrainWest with an existing ID
        trainWest.setId(1L);
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);

        int databaseSizeBeforeCreate = trainWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainWestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainWestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainWests() throws Exception {
        // Initialize the database
        trainWestRepository.saveAndFlush(trainWest);

        // Get all the trainWestList
        restTrainWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].numberOfSeats").value(hasItem(DEFAULT_NUMBER_OF_SEATS.intValue())))
            .andExpect(jsonPath("$.[*].fuelTypeId").value(hasItem(DEFAULT_FUEL_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainTypeId").value(hasItem(DEFAULT_TRAIN_TYPE_ID.intValue())));
    }

    @Test
    @Transactional
    void getTrainWest() throws Exception {
        // Initialize the database
        trainWestRepository.saveAndFlush(trainWest);

        // Get the trainWest
        restTrainWestMockMvc
            .perform(get(ENTITY_API_URL_ID, trainWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainWest.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.numberOfSeats").value(DEFAULT_NUMBER_OF_SEATS.intValue()))
            .andExpect(jsonPath("$.fuelTypeId").value(DEFAULT_FUEL_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.trainTypeId").value(DEFAULT_TRAIN_TYPE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTrainWest() throws Exception {
        // Get the trainWest
        restTrainWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainWest() throws Exception {
        // Initialize the database
        trainWestRepository.saveAndFlush(trainWest);

        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();

        // Update the trainWest
        TrainWest updatedTrainWest = trainWestRepository.findById(trainWest.getId()).get();
        // Disconnect from session so that the updates on updatedTrainWest are not directly saved in db
        em.detach(updatedTrainWest);
        updatedTrainWest
            .code(UPDATED_CODE)
            .numberOfSeats(UPDATED_NUMBER_OF_SEATS)
            .fuelTypeId(UPDATED_FUEL_TYPE_ID)
            .trainTypeId(UPDATED_TRAIN_TYPE_ID);
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(updatedTrainWest);

        restTrainWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
        TrainWest testTrainWest = trainWestList.get(trainWestList.size() - 1);
        assertThat(testTrainWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainWest.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
        assertThat(testTrainWest.getFuelTypeId()).isEqualTo(UPDATED_FUEL_TYPE_ID);
        assertThat(testTrainWest.getTrainTypeId()).isEqualTo(UPDATED_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void putNonExistingTrainWest() throws Exception {
        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();
        trainWest.setId(count.incrementAndGet());

        // Create the TrainWest
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainWest() throws Exception {
        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();
        trainWest.setId(count.incrementAndGet());

        // Create the TrainWest
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainWest() throws Exception {
        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();
        trainWest.setId(count.incrementAndGet());

        // Create the TrainWest
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainWestWithPatch() throws Exception {
        // Initialize the database
        trainWestRepository.saveAndFlush(trainWest);

        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();

        // Update the trainWest using partial update
        TrainWest partialUpdatedTrainWest = new TrainWest();
        partialUpdatedTrainWest.setId(trainWest.getId());

        partialUpdatedTrainWest.code(UPDATED_CODE).numberOfSeats(UPDATED_NUMBER_OF_SEATS);

        restTrainWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainWest))
            )
            .andExpect(status().isOk());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
        TrainWest testTrainWest = trainWestList.get(trainWestList.size() - 1);
        assertThat(testTrainWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainWest.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
        assertThat(testTrainWest.getFuelTypeId()).isEqualTo(DEFAULT_FUEL_TYPE_ID);
        assertThat(testTrainWest.getTrainTypeId()).isEqualTo(DEFAULT_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void fullUpdateTrainWestWithPatch() throws Exception {
        // Initialize the database
        trainWestRepository.saveAndFlush(trainWest);

        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();

        // Update the trainWest using partial update
        TrainWest partialUpdatedTrainWest = new TrainWest();
        partialUpdatedTrainWest.setId(trainWest.getId());

        partialUpdatedTrainWest
            .code(UPDATED_CODE)
            .numberOfSeats(UPDATED_NUMBER_OF_SEATS)
            .fuelTypeId(UPDATED_FUEL_TYPE_ID)
            .trainTypeId(UPDATED_TRAIN_TYPE_ID);

        restTrainWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainWest))
            )
            .andExpect(status().isOk());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
        TrainWest testTrainWest = trainWestList.get(trainWestList.size() - 1);
        assertThat(testTrainWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainWest.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
        assertThat(testTrainWest.getFuelTypeId()).isEqualTo(UPDATED_FUEL_TYPE_ID);
        assertThat(testTrainWest.getTrainTypeId()).isEqualTo(UPDATED_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTrainWest() throws Exception {
        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();
        trainWest.setId(count.incrementAndGet());

        // Create the TrainWest
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainWest() throws Exception {
        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();
        trainWest.setId(count.incrementAndGet());

        // Create the TrainWest
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainWest() throws Exception {
        int databaseSizeBeforeUpdate = trainWestRepository.findAll().size();
        trainWest.setId(count.incrementAndGet());

        // Create the TrainWest
        TrainWestDTO trainWestDTO = trainWestMapper.toDto(trainWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trainWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainWest in the database
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainWest() throws Exception {
        // Initialize the database
        trainWestRepository.saveAndFlush(trainWest);

        int databaseSizeBeforeDelete = trainWestRepository.findAll().size();

        // Delete the trainWest
        restTrainWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainWest> trainWestList = trainWestRepository.findAll();
        assertThat(trainWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
