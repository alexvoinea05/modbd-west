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
import ro.west.domain.CityWest;
import ro.west.repository.CityWestRepository;
import ro.west.service.dto.CityWestDTO;
import ro.west.service.mapper.CityWestMapper;

/**
 * Integration tests for the {@link CityWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CityWestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DISTRICT_ID = 1L;
    private static final Long UPDATED_DISTRICT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/city-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityWestRepository cityWestRepository;

    @Autowired
    private CityWestMapper cityWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityWestMockMvc;

    private CityWest cityWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityWest createEntity(EntityManager em) {
        CityWest cityWest = new CityWest().name(DEFAULT_NAME).districtId(DEFAULT_DISTRICT_ID);
        return cityWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityWest createUpdatedEntity(EntityManager em) {
        CityWest cityWest = new CityWest().name(UPDATED_NAME).districtId(UPDATED_DISTRICT_ID);
        return cityWest;
    }

    @BeforeEach
    public void initTest() {
        cityWest = createEntity(em);
    }

    @Test
    @Transactional
    void createCityWest() throws Exception {
        int databaseSizeBeforeCreate = cityWestRepository.findAll().size();
        // Create the CityWest
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);
        restCityWestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityWestDTO)))
            .andExpect(status().isCreated());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeCreate + 1);
        CityWest testCityWest = cityWestList.get(cityWestList.size() - 1);
        assertThat(testCityWest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCityWest.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
    }

    @Test
    @Transactional
    void createCityWestWithExistingId() throws Exception {
        // Create the CityWest with an existing ID
        cityWest.setId(1L);
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);

        int databaseSizeBeforeCreate = cityWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityWestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityWestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCityWests() throws Exception {
        // Initialize the database
        cityWestRepository.saveAndFlush(cityWest);

        // Get all the cityWestList
        restCityWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())));
    }

    @Test
    @Transactional
    void getCityWest() throws Exception {
        // Initialize the database
        cityWestRepository.saveAndFlush(cityWest);

        // Get the cityWest
        restCityWestMockMvc
            .perform(get(ENTITY_API_URL_ID, cityWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cityWest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCityWest() throws Exception {
        // Get the cityWest
        restCityWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCityWest() throws Exception {
        // Initialize the database
        cityWestRepository.saveAndFlush(cityWest);

        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();

        // Update the cityWest
        CityWest updatedCityWest = cityWestRepository.findById(cityWest.getId()).get();
        // Disconnect from session so that the updates on updatedCityWest are not directly saved in db
        em.detach(updatedCityWest);
        updatedCityWest.name(UPDATED_NAME).districtId(UPDATED_DISTRICT_ID);
        CityWestDTO cityWestDTO = cityWestMapper.toDto(updatedCityWest);

        restCityWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
        CityWest testCityWest = cityWestList.get(cityWestList.size() - 1);
        assertThat(testCityWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCityWest.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void putNonExistingCityWest() throws Exception {
        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();
        cityWest.setId(count.incrementAndGet());

        // Create the CityWest
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCityWest() throws Exception {
        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();
        cityWest.setId(count.incrementAndGet());

        // Create the CityWest
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCityWest() throws Exception {
        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();
        cityWest.setId(count.incrementAndGet());

        // Create the CityWest
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCityWestWithPatch() throws Exception {
        // Initialize the database
        cityWestRepository.saveAndFlush(cityWest);

        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();

        // Update the cityWest using partial update
        CityWest partialUpdatedCityWest = new CityWest();
        partialUpdatedCityWest.setId(cityWest.getId());

        partialUpdatedCityWest.name(UPDATED_NAME).districtId(UPDATED_DISTRICT_ID);

        restCityWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityWest))
            )
            .andExpect(status().isOk());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
        CityWest testCityWest = cityWestList.get(cityWestList.size() - 1);
        assertThat(testCityWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCityWest.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void fullUpdateCityWestWithPatch() throws Exception {
        // Initialize the database
        cityWestRepository.saveAndFlush(cityWest);

        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();

        // Update the cityWest using partial update
        CityWest partialUpdatedCityWest = new CityWest();
        partialUpdatedCityWest.setId(cityWest.getId());

        partialUpdatedCityWest.name(UPDATED_NAME).districtId(UPDATED_DISTRICT_ID);

        restCityWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityWest))
            )
            .andExpect(status().isOk());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
        CityWest testCityWest = cityWestList.get(cityWestList.size() - 1);
        assertThat(testCityWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCityWest.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCityWest() throws Exception {
        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();
        cityWest.setId(count.incrementAndGet());

        // Create the CityWest
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCityWest() throws Exception {
        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();
        cityWest.setId(count.incrementAndGet());

        // Create the CityWest
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCityWest() throws Exception {
        int databaseSizeBeforeUpdate = cityWestRepository.findAll().size();
        cityWest.setId(count.incrementAndGet());

        // Create the CityWest
        CityWestDTO cityWestDTO = cityWestMapper.toDto(cityWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cityWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityWest in the database
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCityWest() throws Exception {
        // Initialize the database
        cityWestRepository.saveAndFlush(cityWest);

        int databaseSizeBeforeDelete = cityWestRepository.findAll().size();

        // Delete the cityWest
        restCityWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, cityWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CityWest> cityWestList = cityWestRepository.findAll();
        assertThat(cityWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
