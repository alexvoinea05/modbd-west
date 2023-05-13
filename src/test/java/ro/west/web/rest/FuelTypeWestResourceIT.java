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
import ro.west.domain.FuelTypeWest;
import ro.west.repository.FuelTypeWestRepository;
import ro.west.service.dto.FuelTypeWestDTO;
import ro.west.service.mapper.FuelTypeWestMapper;

/**
 * Integration tests for the {@link FuelTypeWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelTypeWestResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fuel-type-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuelTypeWestRepository fuelTypeWestRepository;

    @Autowired
    private FuelTypeWestMapper fuelTypeWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelTypeWestMockMvc;

    private FuelTypeWest fuelTypeWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTypeWest createEntity(EntityManager em) {
        FuelTypeWest fuelTypeWest = new FuelTypeWest().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return fuelTypeWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTypeWest createUpdatedEntity(EntityManager em) {
        FuelTypeWest fuelTypeWest = new FuelTypeWest().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return fuelTypeWest;
    }

    @BeforeEach
    public void initTest() {
        fuelTypeWest = createEntity(em);
    }

    @Test
    @Transactional
    void createFuelTypeWest() throws Exception {
        int databaseSizeBeforeCreate = fuelTypeWestRepository.findAll().size();
        // Create the FuelTypeWest
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);
        restFuelTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeCreate + 1);
        FuelTypeWest testFuelTypeWest = fuelTypeWestList.get(fuelTypeWestList.size() - 1);
        assertThat(testFuelTypeWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFuelTypeWest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFuelTypeWestWithExistingId() throws Exception {
        // Create the FuelTypeWest with an existing ID
        fuelTypeWest.setId(1L);
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);

        int databaseSizeBeforeCreate = fuelTypeWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelTypeWests() throws Exception {
        // Initialize the database
        fuelTypeWestRepository.saveAndFlush(fuelTypeWest);

        // Get all the fuelTypeWestList
        restFuelTypeWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelTypeWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getFuelTypeWest() throws Exception {
        // Initialize the database
        fuelTypeWestRepository.saveAndFlush(fuelTypeWest);

        // Get the fuelTypeWest
        restFuelTypeWestMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelTypeWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelTypeWest.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFuelTypeWest() throws Exception {
        // Get the fuelTypeWest
        restFuelTypeWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelTypeWest() throws Exception {
        // Initialize the database
        fuelTypeWestRepository.saveAndFlush(fuelTypeWest);

        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();

        // Update the fuelTypeWest
        FuelTypeWest updatedFuelTypeWest = fuelTypeWestRepository.findById(fuelTypeWest.getId()).get();
        // Disconnect from session so that the updates on updatedFuelTypeWest are not directly saved in db
        em.detach(updatedFuelTypeWest);
        updatedFuelTypeWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(updatedFuelTypeWest);

        restFuelTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
        FuelTypeWest testFuelTypeWest = fuelTypeWestList.get(fuelTypeWestList.size() - 1);
        assertThat(testFuelTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFuelTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFuelTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();
        fuelTypeWest.setId(count.incrementAndGet());

        // Create the FuelTypeWest
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();
        fuelTypeWest.setId(count.incrementAndGet());

        // Create the FuelTypeWest
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();
        fuelTypeWest.setId(count.incrementAndGet());

        // Create the FuelTypeWest
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelTypeWestWithPatch() throws Exception {
        // Initialize the database
        fuelTypeWestRepository.saveAndFlush(fuelTypeWest);

        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();

        // Update the fuelTypeWest using partial update
        FuelTypeWest partialUpdatedFuelTypeWest = new FuelTypeWest();
        partialUpdatedFuelTypeWest.setId(fuelTypeWest.getId());

        partialUpdatedFuelTypeWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restFuelTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
        FuelTypeWest testFuelTypeWest = fuelTypeWestList.get(fuelTypeWestList.size() - 1);
        assertThat(testFuelTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFuelTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFuelTypeWestWithPatch() throws Exception {
        // Initialize the database
        fuelTypeWestRepository.saveAndFlush(fuelTypeWest);

        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();

        // Update the fuelTypeWest using partial update
        FuelTypeWest partialUpdatedFuelTypeWest = new FuelTypeWest();
        partialUpdatedFuelTypeWest.setId(fuelTypeWest.getId());

        partialUpdatedFuelTypeWest.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restFuelTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
        FuelTypeWest testFuelTypeWest = fuelTypeWestList.get(fuelTypeWestList.size() - 1);
        assertThat(testFuelTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFuelTypeWest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFuelTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();
        fuelTypeWest.setId(count.incrementAndGet());

        // Create the FuelTypeWest
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelTypeWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();
        fuelTypeWest.setId(count.incrementAndGet());

        // Create the FuelTypeWest
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeWestRepository.findAll().size();
        fuelTypeWest.setId(count.incrementAndGet());

        // Create the FuelTypeWest
        FuelTypeWestDTO fuelTypeWestDTO = fuelTypeWestMapper.toDto(fuelTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTypeWest in the database
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelTypeWest() throws Exception {
        // Initialize the database
        fuelTypeWestRepository.saveAndFlush(fuelTypeWest);

        int databaseSizeBeforeDelete = fuelTypeWestRepository.findAll().size();

        // Delete the fuelTypeWest
        restFuelTypeWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelTypeWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuelTypeWest> fuelTypeWestList = fuelTypeWestRepository.findAll();
        assertThat(fuelTypeWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
