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
import ro.west.domain.RailwayTypeWest;
import ro.west.repository.RailwayTypeWestRepository;
import ro.west.service.dto.RailwayTypeWestDTO;
import ro.west.service.mapper.RailwayTypeWestMapper;

/**
 * Integration tests for the {@link RailwayTypeWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RailwayTypeWestResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/railway-type-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RailwayTypeWestRepository railwayTypeWestRepository;

    @Autowired
    private RailwayTypeWestMapper railwayTypeWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRailwayTypeWestMockMvc;

    private RailwayTypeWest railwayTypeWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayTypeWest createEntity(EntityManager em) {
        RailwayTypeWest railwayTypeWest = new RailwayTypeWest().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return railwayTypeWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayTypeWest createUpdatedEntity(EntityManager em) {
        RailwayTypeWest railwayTypeWest = new RailwayTypeWest().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return railwayTypeWest;
    }

    @BeforeEach
    public void initTest() {
        railwayTypeWest = createEntity(em);
    }

    @Test
    @Transactional
    void createRailwayTypeWest() throws Exception {
        int databaseSizeBeforeCreate = railwayTypeWestRepository.findAll().size();
        // Create the RailwayTypeWest
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);
        restRailwayTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeCreate + 1);
        RailwayTypeWest testRailwayTypeWest = railwayTypeWestList.get(railwayTypeWestList.size() - 1);
        assertThat(testRailwayTypeWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRailwayTypeWest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRailwayTypeWestWithExistingId() throws Exception {
        // Create the RailwayTypeWest with an existing ID
        railwayTypeWest.setId(1L);
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);

        int databaseSizeBeforeCreate = railwayTypeWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRailwayTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRailwayTypeWests() throws Exception {
        // Initialize the database
        railwayTypeWestRepository.saveAndFlush(railwayTypeWest);

        // Get all the railwayTypeWestList
        restRailwayTypeWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(railwayTypeWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRailwayTypeWest() throws Exception {
        // Initialize the database
        railwayTypeWestRepository.saveAndFlush(railwayTypeWest);

        // Get the railwayTypeWest
        restRailwayTypeWestMockMvc
            .perform(get(ENTITY_API_URL_ID, railwayTypeWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(railwayTypeWest.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRailwayTypeWest() throws Exception {
        // Get the railwayTypeWest
        restRailwayTypeWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRailwayTypeWest() throws Exception {
        // Initialize the database
        railwayTypeWestRepository.saveAndFlush(railwayTypeWest);

        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();

        // Update the railwayTypeWest
        RailwayTypeWest updatedRailwayTypeWest = railwayTypeWestRepository.findById(railwayTypeWest.getId()).get();
        // Disconnect from session so that the updates on updatedRailwayTypeWest are not directly saved in db
        em.detach(updatedRailwayTypeWest);
        updatedRailwayTypeWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(updatedRailwayTypeWest);

        restRailwayTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
        RailwayTypeWest testRailwayTypeWest = railwayTypeWestList.get(railwayTypeWestList.size() - 1);
        assertThat(testRailwayTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRailwayTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRailwayTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();
        railwayTypeWest.setId(count.incrementAndGet());

        // Create the RailwayTypeWest
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRailwayTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();
        railwayTypeWest.setId(count.incrementAndGet());

        // Create the RailwayTypeWest
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRailwayTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();
        railwayTypeWest.setId(count.incrementAndGet());

        // Create the RailwayTypeWest
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRailwayTypeWestWithPatch() throws Exception {
        // Initialize the database
        railwayTypeWestRepository.saveAndFlush(railwayTypeWest);

        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();

        // Update the railwayTypeWest using partial update
        RailwayTypeWest partialUpdatedRailwayTypeWest = new RailwayTypeWest();
        partialUpdatedRailwayTypeWest.setId(railwayTypeWest.getId());

        partialUpdatedRailwayTypeWest.description(UPDATED_DESCRIPTION);

        restRailwayTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
        RailwayTypeWest testRailwayTypeWest = railwayTypeWestList.get(railwayTypeWestList.size() - 1);
        assertThat(testRailwayTypeWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRailwayTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRailwayTypeWestWithPatch() throws Exception {
        // Initialize the database
        railwayTypeWestRepository.saveAndFlush(railwayTypeWest);

        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();

        // Update the railwayTypeWest using partial update
        RailwayTypeWest partialUpdatedRailwayTypeWest = new RailwayTypeWest();
        partialUpdatedRailwayTypeWest.setId(railwayTypeWest.getId());

        partialUpdatedRailwayTypeWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restRailwayTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
        RailwayTypeWest testRailwayTypeWest = railwayTypeWestList.get(railwayTypeWestList.size() - 1);
        assertThat(testRailwayTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRailwayTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRailwayTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();
        railwayTypeWest.setId(count.incrementAndGet());

        // Create the RailwayTypeWest
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, railwayTypeWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRailwayTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();
        railwayTypeWest.setId(count.incrementAndGet());

        // Create the RailwayTypeWest
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRailwayTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeWestRepository.findAll().size();
        railwayTypeWest.setId(count.incrementAndGet());

        // Create the RailwayTypeWest
        RailwayTypeWestDTO railwayTypeWestDTO = railwayTypeWestMapper.toDto(railwayTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayTypeWest in the database
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRailwayTypeWest() throws Exception {
        // Initialize the database
        railwayTypeWestRepository.saveAndFlush(railwayTypeWest);

        int databaseSizeBeforeDelete = railwayTypeWestRepository.findAll().size();

        // Delete the railwayTypeWest
        restRailwayTypeWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, railwayTypeWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RailwayTypeWest> railwayTypeWestList = railwayTypeWestRepository.findAll();
        assertThat(railwayTypeWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
