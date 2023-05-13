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
import ro.west.domain.DistrictWest;
import ro.west.repository.DistrictWestRepository;
import ro.west.service.dto.DistrictWestDTO;
import ro.west.service.mapper.DistrictWestMapper;

/**
 * Integration tests for the {@link DistrictWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistrictWestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/district-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DistrictWestRepository districtWestRepository;

    @Autowired
    private DistrictWestMapper districtWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictWestMockMvc;

    private DistrictWest districtWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictWest createEntity(EntityManager em) {
        DistrictWest districtWest = new DistrictWest().name(DEFAULT_NAME).region(DEFAULT_REGION);
        return districtWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictWest createUpdatedEntity(EntityManager em) {
        DistrictWest districtWest = new DistrictWest().name(UPDATED_NAME).region(UPDATED_REGION);
        return districtWest;
    }

    @BeforeEach
    public void initTest() {
        districtWest = createEntity(em);
    }

    @Test
    @Transactional
    void createDistrictWest() throws Exception {
        int databaseSizeBeforeCreate = districtWestRepository.findAll().size();
        // Create the DistrictWest
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);
        restDistrictWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeCreate + 1);
        DistrictWest testDistrictWest = districtWestList.get(districtWestList.size() - 1);
        assertThat(testDistrictWest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistrictWest.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void createDistrictWestWithExistingId() throws Exception {
        // Create the DistrictWest with an existing ID
        districtWest.setId(1L);
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);

        int databaseSizeBeforeCreate = districtWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDistrictWests() throws Exception {
        // Initialize the database
        districtWestRepository.saveAndFlush(districtWest);

        // Get all the districtWestList
        restDistrictWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)));
    }

    @Test
    @Transactional
    void getDistrictWest() throws Exception {
        // Initialize the database
        districtWestRepository.saveAndFlush(districtWest);

        // Get the districtWest
        restDistrictWestMockMvc
            .perform(get(ENTITY_API_URL_ID, districtWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districtWest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION));
    }

    @Test
    @Transactional
    void getNonExistingDistrictWest() throws Exception {
        // Get the districtWest
        restDistrictWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDistrictWest() throws Exception {
        // Initialize the database
        districtWestRepository.saveAndFlush(districtWest);

        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();

        // Update the districtWest
        DistrictWest updatedDistrictWest = districtWestRepository.findById(districtWest.getId()).get();
        // Disconnect from session so that the updates on updatedDistrictWest are not directly saved in db
        em.detach(updatedDistrictWest);
        updatedDistrictWest.name(UPDATED_NAME).region(UPDATED_REGION);
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(updatedDistrictWest);

        restDistrictWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
        DistrictWest testDistrictWest = districtWestList.get(districtWestList.size() - 1);
        assertThat(testDistrictWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistrictWest.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void putNonExistingDistrictWest() throws Exception {
        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();
        districtWest.setId(count.incrementAndGet());

        // Create the DistrictWest
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistrictWest() throws Exception {
        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();
        districtWest.setId(count.incrementAndGet());

        // Create the DistrictWest
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistrictWest() throws Exception {
        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();
        districtWest.setId(count.incrementAndGet());

        // Create the DistrictWest
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictWestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistrictWestWithPatch() throws Exception {
        // Initialize the database
        districtWestRepository.saveAndFlush(districtWest);

        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();

        // Update the districtWest using partial update
        DistrictWest partialUpdatedDistrictWest = new DistrictWest();
        partialUpdatedDistrictWest.setId(districtWest.getId());

        partialUpdatedDistrictWest.name(UPDATED_NAME);

        restDistrictWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrictWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrictWest))
            )
            .andExpect(status().isOk());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
        DistrictWest testDistrictWest = districtWestList.get(districtWestList.size() - 1);
        assertThat(testDistrictWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistrictWest.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void fullUpdateDistrictWestWithPatch() throws Exception {
        // Initialize the database
        districtWestRepository.saveAndFlush(districtWest);

        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();

        // Update the districtWest using partial update
        DistrictWest partialUpdatedDistrictWest = new DistrictWest();
        partialUpdatedDistrictWest.setId(districtWest.getId());

        partialUpdatedDistrictWest.name(UPDATED_NAME).region(UPDATED_REGION);

        restDistrictWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrictWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrictWest))
            )
            .andExpect(status().isOk());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
        DistrictWest testDistrictWest = districtWestList.get(districtWestList.size() - 1);
        assertThat(testDistrictWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistrictWest.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void patchNonExistingDistrictWest() throws Exception {
        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();
        districtWest.setId(count.incrementAndGet());

        // Create the DistrictWest
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districtWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistrictWest() throws Exception {
        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();
        districtWest.setId(count.incrementAndGet());

        // Create the DistrictWest
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistrictWest() throws Exception {
        int databaseSizeBeforeUpdate = districtWestRepository.findAll().size();
        districtWest.setId(count.incrementAndGet());

        // Create the DistrictWest
        DistrictWestDTO districtWestDTO = districtWestMapper.toDto(districtWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistrictWest in the database
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistrictWest() throws Exception {
        // Initialize the database
        districtWestRepository.saveAndFlush(districtWest);

        int databaseSizeBeforeDelete = districtWestRepository.findAll().size();

        // Delete the districtWest
        restDistrictWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, districtWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DistrictWest> districtWestList = districtWestRepository.findAll();
        assertThat(districtWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
