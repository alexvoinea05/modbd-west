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
import ro.west.domain.TrainTypeWest;
import ro.west.repository.TrainTypeWestRepository;
import ro.west.service.dto.TrainTypeWestDTO;
import ro.west.service.mapper.TrainTypeWestMapper;

/**
 * Integration tests for the {@link TrainTypeWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainTypeWestResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/train-type-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainTypeWestRepository trainTypeWestRepository;

    @Autowired
    private TrainTypeWestMapper trainTypeWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainTypeWestMockMvc;

    private TrainTypeWest trainTypeWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainTypeWest createEntity(EntityManager em) {
        TrainTypeWest trainTypeWest = new TrainTypeWest().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return trainTypeWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainTypeWest createUpdatedEntity(EntityManager em) {
        TrainTypeWest trainTypeWest = new TrainTypeWest().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return trainTypeWest;
    }

    @BeforeEach
    public void initTest() {
        trainTypeWest = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainTypeWest() throws Exception {
        int databaseSizeBeforeCreate = trainTypeWestRepository.findAll().size();
        // Create the TrainTypeWest
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);
        restTrainTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeCreate + 1);
        TrainTypeWest testTrainTypeWest = trainTypeWestList.get(trainTypeWestList.size() - 1);
        assertThat(testTrainTypeWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrainTypeWest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTrainTypeWestWithExistingId() throws Exception {
        // Create the TrainTypeWest with an existing ID
        trainTypeWest.setId(1L);
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);

        int databaseSizeBeforeCreate = trainTypeWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainTypeWests() throws Exception {
        // Initialize the database
        trainTypeWestRepository.saveAndFlush(trainTypeWest);

        // Get all the trainTypeWestList
        restTrainTypeWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainTypeWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTrainTypeWest() throws Exception {
        // Initialize the database
        trainTypeWestRepository.saveAndFlush(trainTypeWest);

        // Get the trainTypeWest
        restTrainTypeWestMockMvc
            .perform(get(ENTITY_API_URL_ID, trainTypeWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainTypeWest.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTrainTypeWest() throws Exception {
        // Get the trainTypeWest
        restTrainTypeWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainTypeWest() throws Exception {
        // Initialize the database
        trainTypeWestRepository.saveAndFlush(trainTypeWest);

        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();

        // Update the trainTypeWest
        TrainTypeWest updatedTrainTypeWest = trainTypeWestRepository.findById(trainTypeWest.getId()).get();
        // Disconnect from session so that the updates on updatedTrainTypeWest are not directly saved in db
        em.detach(updatedTrainTypeWest);
        updatedTrainTypeWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(updatedTrainTypeWest);

        restTrainTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
        TrainTypeWest testTrainTypeWest = trainTypeWestList.get(trainTypeWestList.size() - 1);
        assertThat(testTrainTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTrainTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();
        trainTypeWest.setId(count.incrementAndGet());

        // Create the TrainTypeWest
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();
        trainTypeWest.setId(count.incrementAndGet());

        // Create the TrainTypeWest
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();
        trainTypeWest.setId(count.incrementAndGet());

        // Create the TrainTypeWest
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainTypeWestWithPatch() throws Exception {
        // Initialize the database
        trainTypeWestRepository.saveAndFlush(trainTypeWest);

        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();

        // Update the trainTypeWest using partial update
        TrainTypeWest partialUpdatedTrainTypeWest = new TrainTypeWest();
        partialUpdatedTrainTypeWest.setId(trainTypeWest.getId());

        partialUpdatedTrainTypeWest.code(UPDATED_CODE);

        restTrainTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
        TrainTypeWest testTrainTypeWest = trainTypeWestList.get(trainTypeWestList.size() - 1);
        assertThat(testTrainTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainTypeWest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTrainTypeWestWithPatch() throws Exception {
        // Initialize the database
        trainTypeWestRepository.saveAndFlush(trainTypeWest);

        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();

        // Update the trainTypeWest using partial update
        TrainTypeWest partialUpdatedTrainTypeWest = new TrainTypeWest();
        partialUpdatedTrainTypeWest.setId(trainTypeWest.getId());

        partialUpdatedTrainTypeWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTrainTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
        TrainTypeWest testTrainTypeWest = trainTypeWestList.get(trainTypeWestList.size() - 1);
        assertThat(testTrainTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTrainTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();
        trainTypeWest.setId(count.incrementAndGet());

        // Create the TrainTypeWest
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainTypeWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();
        trainTypeWest.setId(count.incrementAndGet());

        // Create the TrainTypeWest
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeWestRepository.findAll().size();
        trainTypeWest.setId(count.incrementAndGet());

        // Create the TrainTypeWest
        TrainTypeWestDTO trainTypeWestDTO = trainTypeWestMapper.toDto(trainTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainTypeWest in the database
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainTypeWest() throws Exception {
        // Initialize the database
        trainTypeWestRepository.saveAndFlush(trainTypeWest);

        int databaseSizeBeforeDelete = trainTypeWestRepository.findAll().size();

        // Delete the trainTypeWest
        restTrainTypeWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainTypeWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainTypeWest> trainTypeWestList = trainTypeWestRepository.findAll();
        assertThat(trainTypeWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
