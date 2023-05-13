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
import ro.west.domain.JourneyStatusWest;
import ro.west.repository.JourneyStatusWestRepository;
import ro.west.service.dto.JourneyStatusWestDTO;
import ro.west.service.mapper.JourneyStatusWestMapper;

/**
 * Integration tests for the {@link JourneyStatusWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JourneyStatusWestResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/journey-status-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JourneyStatusWestRepository journeyStatusWestRepository;

    @Autowired
    private JourneyStatusWestMapper journeyStatusWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJourneyStatusWestMockMvc;

    private JourneyStatusWest journeyStatusWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyStatusWest createEntity(EntityManager em) {
        JourneyStatusWest journeyStatusWest = new JourneyStatusWest().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return journeyStatusWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyStatusWest createUpdatedEntity(EntityManager em) {
        JourneyStatusWest journeyStatusWest = new JourneyStatusWest().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return journeyStatusWest;
    }

    @BeforeEach
    public void initTest() {
        journeyStatusWest = createEntity(em);
    }

    @Test
    @Transactional
    void createJourneyStatusWest() throws Exception {
        int databaseSizeBeforeCreate = journeyStatusWestRepository.findAll().size();
        // Create the JourneyStatusWest
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);
        restJourneyStatusWestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeCreate + 1);
        JourneyStatusWest testJourneyStatusWest = journeyStatusWestList.get(journeyStatusWestList.size() - 1);
        assertThat(testJourneyStatusWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJourneyStatusWest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createJourneyStatusWestWithExistingId() throws Exception {
        // Create the JourneyStatusWest with an existing ID
        journeyStatusWest.setId(1L);
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);

        int databaseSizeBeforeCreate = journeyStatusWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyStatusWestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJourneyStatusWests() throws Exception {
        // Initialize the database
        journeyStatusWestRepository.saveAndFlush(journeyStatusWest);

        // Get all the journeyStatusWestList
        restJourneyStatusWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journeyStatusWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getJourneyStatusWest() throws Exception {
        // Initialize the database
        journeyStatusWestRepository.saveAndFlush(journeyStatusWest);

        // Get the journeyStatusWest
        restJourneyStatusWestMockMvc
            .perform(get(ENTITY_API_URL_ID, journeyStatusWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(journeyStatusWest.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingJourneyStatusWest() throws Exception {
        // Get the journeyStatusWest
        restJourneyStatusWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJourneyStatusWest() throws Exception {
        // Initialize the database
        journeyStatusWestRepository.saveAndFlush(journeyStatusWest);

        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();

        // Update the journeyStatusWest
        JourneyStatusWest updatedJourneyStatusWest = journeyStatusWestRepository.findById(journeyStatusWest.getId()).get();
        // Disconnect from session so that the updates on updatedJourneyStatusWest are not directly saved in db
        em.detach(updatedJourneyStatusWest);
        updatedJourneyStatusWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(updatedJourneyStatusWest);

        restJourneyStatusWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyStatusWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatusWest testJourneyStatusWest = journeyStatusWestList.get(journeyStatusWestList.size() - 1);
        assertThat(testJourneyStatusWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJourneyStatusWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingJourneyStatusWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();
        journeyStatusWest.setId(count.incrementAndGet());

        // Create the JourneyStatusWest
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyStatusWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyStatusWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJourneyStatusWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();
        journeyStatusWest.setId(count.incrementAndGet());

        // Create the JourneyStatusWest
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJourneyStatusWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();
        journeyStatusWest.setId(count.incrementAndGet());

        // Create the JourneyStatusWest
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusWestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJourneyStatusWestWithPatch() throws Exception {
        // Initialize the database
        journeyStatusWestRepository.saveAndFlush(journeyStatusWest);

        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();

        // Update the journeyStatusWest using partial update
        JourneyStatusWest partialUpdatedJourneyStatusWest = new JourneyStatusWest();
        partialUpdatedJourneyStatusWest.setId(journeyStatusWest.getId());

        restJourneyStatusWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyStatusWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyStatusWest))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatusWest testJourneyStatusWest = journeyStatusWestList.get(journeyStatusWestList.size() - 1);
        assertThat(testJourneyStatusWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJourneyStatusWest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateJourneyStatusWestWithPatch() throws Exception {
        // Initialize the database
        journeyStatusWestRepository.saveAndFlush(journeyStatusWest);

        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();

        // Update the journeyStatusWest using partial update
        JourneyStatusWest partialUpdatedJourneyStatusWest = new JourneyStatusWest();
        partialUpdatedJourneyStatusWest.setId(journeyStatusWest.getId());

        partialUpdatedJourneyStatusWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restJourneyStatusWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyStatusWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyStatusWest))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatusWest testJourneyStatusWest = journeyStatusWestList.get(journeyStatusWestList.size() - 1);
        assertThat(testJourneyStatusWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJourneyStatusWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingJourneyStatusWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();
        journeyStatusWest.setId(count.incrementAndGet());

        // Create the JourneyStatusWest
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyStatusWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, journeyStatusWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJourneyStatusWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();
        journeyStatusWest.setId(count.incrementAndGet());

        // Create the JourneyStatusWest
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJourneyStatusWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusWestRepository.findAll().size();
        journeyStatusWest.setId(count.incrementAndGet());

        // Create the JourneyStatusWest
        JourneyStatusWestDTO journeyStatusWestDTO = journeyStatusWestMapper.toDto(journeyStatusWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyStatusWest in the database
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJourneyStatusWest() throws Exception {
        // Initialize the database
        journeyStatusWestRepository.saveAndFlush(journeyStatusWest);

        int databaseSizeBeforeDelete = journeyStatusWestRepository.findAll().size();

        // Delete the journeyStatusWest
        restJourneyStatusWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, journeyStatusWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourneyStatusWest> journeyStatusWestList = journeyStatusWestRepository.findAll();
        assertThat(journeyStatusWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
