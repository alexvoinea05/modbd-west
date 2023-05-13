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
import ro.west.domain.LicenseWest;
import ro.west.repository.LicenseWestRepository;
import ro.west.service.dto.LicenseWestDTO;
import ro.west.service.mapper.LicenseWestMapper;

/**
 * Integration tests for the {@link LicenseWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LicenseWestResourceIT {

    private static final Long DEFAULT_LICENSE_NUMBER = 1L;
    private static final Long UPDATED_LICENSE_NUMBER = 2L;

    private static final String DEFAULT_LICENSE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/license-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LicenseWestRepository licenseWestRepository;

    @Autowired
    private LicenseWestMapper licenseWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicenseWestMockMvc;

    private LicenseWest licenseWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenseWest createEntity(EntityManager em) {
        LicenseWest licenseWest = new LicenseWest().licenseNumber(DEFAULT_LICENSE_NUMBER).licenseDescription(DEFAULT_LICENSE_DESCRIPTION);
        return licenseWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenseWest createUpdatedEntity(EntityManager em) {
        LicenseWest licenseWest = new LicenseWest().licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);
        return licenseWest;
    }

    @BeforeEach
    public void initTest() {
        licenseWest = createEntity(em);
    }

    @Test
    @Transactional
    void createLicenseWest() throws Exception {
        int databaseSizeBeforeCreate = licenseWestRepository.findAll().size();
        // Create the LicenseWest
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);
        restLicenseWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeCreate + 1);
        LicenseWest testLicenseWest = licenseWestList.get(licenseWestList.size() - 1);
        assertThat(testLicenseWest.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testLicenseWest.getLicenseDescription()).isEqualTo(DEFAULT_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void createLicenseWestWithExistingId() throws Exception {
        // Create the LicenseWest with an existing ID
        licenseWest.setId(1L);
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);

        int databaseSizeBeforeCreate = licenseWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenseWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLicenseWests() throws Exception {
        // Initialize the database
        licenseWestRepository.saveAndFlush(licenseWest);

        // Get all the licenseWestList
        restLicenseWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenseWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].licenseDescription").value(hasItem(DEFAULT_LICENSE_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getLicenseWest() throws Exception {
        // Initialize the database
        licenseWestRepository.saveAndFlush(licenseWest);

        // Get the licenseWest
        restLicenseWestMockMvc
            .perform(get(ENTITY_API_URL_ID, licenseWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licenseWest.getId().intValue()))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.intValue()))
            .andExpect(jsonPath("$.licenseDescription").value(DEFAULT_LICENSE_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingLicenseWest() throws Exception {
        // Get the licenseWest
        restLicenseWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLicenseWest() throws Exception {
        // Initialize the database
        licenseWestRepository.saveAndFlush(licenseWest);

        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();

        // Update the licenseWest
        LicenseWest updatedLicenseWest = licenseWestRepository.findById(licenseWest.getId()).get();
        // Disconnect from session so that the updates on updatedLicenseWest are not directly saved in db
        em.detach(updatedLicenseWest);
        updatedLicenseWest.licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(updatedLicenseWest);

        restLicenseWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licenseWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
        LicenseWest testLicenseWest = licenseWestList.get(licenseWestList.size() - 1);
        assertThat(testLicenseWest.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicenseWest.getLicenseDescription()).isEqualTo(UPDATED_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();
        licenseWest.setId(count.incrementAndGet());

        // Create the LicenseWest
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licenseWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();
        licenseWest.setId(count.incrementAndGet());

        // Create the LicenseWest
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();
        licenseWest.setId(count.incrementAndGet());

        // Create the LicenseWest
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLicenseWestWithPatch() throws Exception {
        // Initialize the database
        licenseWestRepository.saveAndFlush(licenseWest);

        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();

        // Update the licenseWest using partial update
        LicenseWest partialUpdatedLicenseWest = new LicenseWest();
        partialUpdatedLicenseWest.setId(licenseWest.getId());

        partialUpdatedLicenseWest.licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);

        restLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicenseWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicenseWest))
            )
            .andExpect(status().isOk());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
        LicenseWest testLicenseWest = licenseWestList.get(licenseWestList.size() - 1);
        assertThat(testLicenseWest.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicenseWest.getLicenseDescription()).isEqualTo(UPDATED_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateLicenseWestWithPatch() throws Exception {
        // Initialize the database
        licenseWestRepository.saveAndFlush(licenseWest);

        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();

        // Update the licenseWest using partial update
        LicenseWest partialUpdatedLicenseWest = new LicenseWest();
        partialUpdatedLicenseWest.setId(licenseWest.getId());

        partialUpdatedLicenseWest.licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);

        restLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicenseWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicenseWest))
            )
            .andExpect(status().isOk());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
        LicenseWest testLicenseWest = licenseWestList.get(licenseWestList.size() - 1);
        assertThat(testLicenseWest.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicenseWest.getLicenseDescription()).isEqualTo(UPDATED_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();
        licenseWest.setId(count.incrementAndGet());

        // Create the LicenseWest
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, licenseWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();
        licenseWest.setId(count.incrementAndGet());

        // Create the LicenseWest
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = licenseWestRepository.findAll().size();
        licenseWest.setId(count.incrementAndGet());

        // Create the LicenseWest
        LicenseWestDTO licenseWestDTO = licenseWestMapper.toDto(licenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(licenseWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LicenseWest in the database
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLicenseWest() throws Exception {
        // Initialize the database
        licenseWestRepository.saveAndFlush(licenseWest);

        int databaseSizeBeforeDelete = licenseWestRepository.findAll().size();

        // Delete the licenseWest
        restLicenseWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, licenseWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LicenseWest> licenseWestList = licenseWestRepository.findAll();
        assertThat(licenseWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
