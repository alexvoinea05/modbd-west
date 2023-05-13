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
import ro.west.domain.RailwayStationWest;
import ro.west.repository.RailwayStationWestRepository;
import ro.west.service.dto.RailwayStationWestDTO;
import ro.west.service.mapper.RailwayStationWestMapper;

/**
 * Integration tests for the {@link RailwayStationWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RailwayStationWestResourceIT {

    private static final String DEFAULT_RAILWAY_STATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RAILWAY_STATION_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_RAILWAY_TYPE_ID = 1L;
    private static final Long UPDATED_RAILWAY_TYPE_ID = 2L;

    private static final Long DEFAULT_ADDRESS_ID = 1L;
    private static final Long UPDATED_ADDRESS_ID = 2L;

    private static final String ENTITY_API_URL = "/api/railway-station-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RailwayStationWestRepository railwayStationWestRepository;

    @Autowired
    private RailwayStationWestMapper railwayStationWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRailwayStationWestMockMvc;

    private RailwayStationWest railwayStationWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayStationWest createEntity(EntityManager em) {
        RailwayStationWest railwayStationWest = new RailwayStationWest()
            .railwayStationName(DEFAULT_RAILWAY_STATION_NAME)
            .railwayTypeId(DEFAULT_RAILWAY_TYPE_ID)
            .addressId(DEFAULT_ADDRESS_ID);
        return railwayStationWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayStationWest createUpdatedEntity(EntityManager em) {
        RailwayStationWest railwayStationWest = new RailwayStationWest()
            .railwayStationName(UPDATED_RAILWAY_STATION_NAME)
            .railwayTypeId(UPDATED_RAILWAY_TYPE_ID)
            .addressId(UPDATED_ADDRESS_ID);
        return railwayStationWest;
    }

    @BeforeEach
    public void initTest() {
        railwayStationWest = createEntity(em);
    }

    @Test
    @Transactional
    void createRailwayStationWest() throws Exception {
        int databaseSizeBeforeCreate = railwayStationWestRepository.findAll().size();
        // Create the RailwayStationWest
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);
        restRailwayStationWestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeCreate + 1);
        RailwayStationWest testRailwayStationWest = railwayStationWestList.get(railwayStationWestList.size() - 1);
        assertThat(testRailwayStationWest.getRailwayStationName()).isEqualTo(DEFAULT_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationWest.getRailwayTypeId()).isEqualTo(DEFAULT_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationWest.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void createRailwayStationWestWithExistingId() throws Exception {
        // Create the RailwayStationWest with an existing ID
        railwayStationWest.setId(1L);
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);

        int databaseSizeBeforeCreate = railwayStationWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRailwayStationWestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRailwayStationWests() throws Exception {
        // Initialize the database
        railwayStationWestRepository.saveAndFlush(railwayStationWest);

        // Get all the railwayStationWestList
        restRailwayStationWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(railwayStationWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].railwayStationName").value(hasItem(DEFAULT_RAILWAY_STATION_NAME)))
            .andExpect(jsonPath("$.[*].railwayTypeId").value(hasItem(DEFAULT_RAILWAY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.intValue())));
    }

    @Test
    @Transactional
    void getRailwayStationWest() throws Exception {
        // Initialize the database
        railwayStationWestRepository.saveAndFlush(railwayStationWest);

        // Get the railwayStationWest
        restRailwayStationWestMockMvc
            .perform(get(ENTITY_API_URL_ID, railwayStationWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(railwayStationWest.getId().intValue()))
            .andExpect(jsonPath("$.railwayStationName").value(DEFAULT_RAILWAY_STATION_NAME))
            .andExpect(jsonPath("$.railwayTypeId").value(DEFAULT_RAILWAY_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRailwayStationWest() throws Exception {
        // Get the railwayStationWest
        restRailwayStationWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRailwayStationWest() throws Exception {
        // Initialize the database
        railwayStationWestRepository.saveAndFlush(railwayStationWest);

        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();

        // Update the railwayStationWest
        RailwayStationWest updatedRailwayStationWest = railwayStationWestRepository.findById(railwayStationWest.getId()).get();
        // Disconnect from session so that the updates on updatedRailwayStationWest are not directly saved in db
        em.detach(updatedRailwayStationWest);
        updatedRailwayStationWest
            .railwayStationName(UPDATED_RAILWAY_STATION_NAME)
            .railwayTypeId(UPDATED_RAILWAY_TYPE_ID)
            .addressId(UPDATED_ADDRESS_ID);
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(updatedRailwayStationWest);

        restRailwayStationWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayStationWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
        RailwayStationWest testRailwayStationWest = railwayStationWestList.get(railwayStationWestList.size() - 1);
        assertThat(testRailwayStationWest.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationWest.getRailwayTypeId()).isEqualTo(UPDATED_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationWest.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void putNonExistingRailwayStationWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();
        railwayStationWest.setId(count.incrementAndGet());

        // Create the RailwayStationWest
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayStationWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayStationWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRailwayStationWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();
        railwayStationWest.setId(count.incrementAndGet());

        // Create the RailwayStationWest
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRailwayStationWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();
        railwayStationWest.setId(count.incrementAndGet());

        // Create the RailwayStationWest
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationWestMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRailwayStationWestWithPatch() throws Exception {
        // Initialize the database
        railwayStationWestRepository.saveAndFlush(railwayStationWest);

        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();

        // Update the railwayStationWest using partial update
        RailwayStationWest partialUpdatedRailwayStationWest = new RailwayStationWest();
        partialUpdatedRailwayStationWest.setId(railwayStationWest.getId());

        partialUpdatedRailwayStationWest
            .railwayStationName(UPDATED_RAILWAY_STATION_NAME)
            .railwayTypeId(UPDATED_RAILWAY_TYPE_ID)
            .addressId(UPDATED_ADDRESS_ID);

        restRailwayStationWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayStationWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayStationWest))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
        RailwayStationWest testRailwayStationWest = railwayStationWestList.get(railwayStationWestList.size() - 1);
        assertThat(testRailwayStationWest.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationWest.getRailwayTypeId()).isEqualTo(UPDATED_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationWest.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void fullUpdateRailwayStationWestWithPatch() throws Exception {
        // Initialize the database
        railwayStationWestRepository.saveAndFlush(railwayStationWest);

        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();

        // Update the railwayStationWest using partial update
        RailwayStationWest partialUpdatedRailwayStationWest = new RailwayStationWest();
        partialUpdatedRailwayStationWest.setId(railwayStationWest.getId());

        partialUpdatedRailwayStationWest
            .railwayStationName(UPDATED_RAILWAY_STATION_NAME)
            .railwayTypeId(UPDATED_RAILWAY_TYPE_ID)
            .addressId(UPDATED_ADDRESS_ID);

        restRailwayStationWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayStationWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayStationWest))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
        RailwayStationWest testRailwayStationWest = railwayStationWestList.get(railwayStationWestList.size() - 1);
        assertThat(testRailwayStationWest.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationWest.getRailwayTypeId()).isEqualTo(UPDATED_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationWest.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void patchNonExistingRailwayStationWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();
        railwayStationWest.setId(count.incrementAndGet());

        // Create the RailwayStationWest
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayStationWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, railwayStationWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRailwayStationWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();
        railwayStationWest.setId(count.incrementAndGet());

        // Create the RailwayStationWest
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRailwayStationWest() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationWestRepository.findAll().size();
        railwayStationWest.setId(count.incrementAndGet());

        // Create the RailwayStationWest
        RailwayStationWestDTO railwayStationWestDTO = railwayStationWestMapper.toDto(railwayStationWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayStationWest in the database
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRailwayStationWest() throws Exception {
        // Initialize the database
        railwayStationWestRepository.saveAndFlush(railwayStationWest);

        int databaseSizeBeforeDelete = railwayStationWestRepository.findAll().size();

        // Delete the railwayStationWest
        restRailwayStationWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, railwayStationWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RailwayStationWest> railwayStationWestList = railwayStationWestRepository.findAll();
        assertThat(railwayStationWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
