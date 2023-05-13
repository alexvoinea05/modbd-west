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
import ro.west.domain.CompanyLicenseWest;
import ro.west.repository.CompanyLicenseWestRepository;
import ro.west.service.dto.CompanyLicenseWestDTO;
import ro.west.service.mapper.CompanyLicenseWestMapper;

/**
 * Integration tests for the {@link CompanyLicenseWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyLicenseWestResourceIT {

    private static final String ENTITY_API_URL = "/api/company-license-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyLicenseWestRepository companyLicenseWestRepository;

    @Autowired
    private CompanyLicenseWestMapper companyLicenseWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyLicenseWestMockMvc;

    private CompanyLicenseWest companyLicenseWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLicenseWest createEntity(EntityManager em) {
        CompanyLicenseWest companyLicenseWest = new CompanyLicenseWest();
        return companyLicenseWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLicenseWest createUpdatedEntity(EntityManager em) {
        CompanyLicenseWest companyLicenseWest = new CompanyLicenseWest();
        return companyLicenseWest;
    }

    @BeforeEach
    public void initTest() {
        companyLicenseWest = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyLicenseWest() throws Exception {
        int databaseSizeBeforeCreate = companyLicenseWestRepository.findAll().size();
        // Create the CompanyLicenseWest
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);
        restCompanyLicenseWestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyLicenseWest testCompanyLicenseWest = companyLicenseWestList.get(companyLicenseWestList.size() - 1);
    }

    @Test
    @Transactional
    void createCompanyLicenseWestWithExistingId() throws Exception {
        // Create the CompanyLicenseWest with an existing ID
        companyLicenseWest.setId(1L);
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);

        int databaseSizeBeforeCreate = companyLicenseWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyLicenseWestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyLicenseWests() throws Exception {
        // Initialize the database
        companyLicenseWestRepository.saveAndFlush(companyLicenseWest);

        // Get all the companyLicenseWestList
        restCompanyLicenseWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyLicenseWest.getId().intValue())));
    }

    @Test
    @Transactional
    void getCompanyLicenseWest() throws Exception {
        // Initialize the database
        companyLicenseWestRepository.saveAndFlush(companyLicenseWest);

        // Get the companyLicenseWest
        restCompanyLicenseWestMockMvc
            .perform(get(ENTITY_API_URL_ID, companyLicenseWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyLicenseWest.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCompanyLicenseWest() throws Exception {
        // Get the companyLicenseWest
        restCompanyLicenseWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompanyLicenseWest() throws Exception {
        // Initialize the database
        companyLicenseWestRepository.saveAndFlush(companyLicenseWest);

        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();

        // Update the companyLicenseWest
        CompanyLicenseWest updatedCompanyLicenseWest = companyLicenseWestRepository.findById(companyLicenseWest.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyLicenseWest are not directly saved in db
        em.detach(updatedCompanyLicenseWest);
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(updatedCompanyLicenseWest);

        restCompanyLicenseWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyLicenseWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
        CompanyLicenseWest testCompanyLicenseWest = companyLicenseWestList.get(companyLicenseWestList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCompanyLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();
        companyLicenseWest.setId(count.incrementAndGet());

        // Create the CompanyLicenseWest
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyLicenseWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyLicenseWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();
        companyLicenseWest.setId(count.incrementAndGet());

        // Create the CompanyLicenseWest
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();
        companyLicenseWest.setId(count.incrementAndGet());

        // Create the CompanyLicenseWest
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseWestMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyLicenseWestWithPatch() throws Exception {
        // Initialize the database
        companyLicenseWestRepository.saveAndFlush(companyLicenseWest);

        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();

        // Update the companyLicenseWest using partial update
        CompanyLicenseWest partialUpdatedCompanyLicenseWest = new CompanyLicenseWest();
        partialUpdatedCompanyLicenseWest.setId(companyLicenseWest.getId());

        restCompanyLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyLicenseWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyLicenseWest))
            )
            .andExpect(status().isOk());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
        CompanyLicenseWest testCompanyLicenseWest = companyLicenseWestList.get(companyLicenseWestList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCompanyLicenseWestWithPatch() throws Exception {
        // Initialize the database
        companyLicenseWestRepository.saveAndFlush(companyLicenseWest);

        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();

        // Update the companyLicenseWest using partial update
        CompanyLicenseWest partialUpdatedCompanyLicenseWest = new CompanyLicenseWest();
        partialUpdatedCompanyLicenseWest.setId(companyLicenseWest.getId());

        restCompanyLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyLicenseWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyLicenseWest))
            )
            .andExpect(status().isOk());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
        CompanyLicenseWest testCompanyLicenseWest = companyLicenseWestList.get(companyLicenseWestList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();
        companyLicenseWest.setId(count.incrementAndGet());

        // Create the CompanyLicenseWest
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyLicenseWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();
        companyLicenseWest.setId(count.incrementAndGet());

        // Create the CompanyLicenseWest
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyLicenseWest() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseWestRepository.findAll().size();
        companyLicenseWest.setId(count.incrementAndGet());

        // Create the CompanyLicenseWest
        CompanyLicenseWestDTO companyLicenseWestDTO = companyLicenseWestMapper.toDto(companyLicenseWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyLicenseWest in the database
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyLicenseWest() throws Exception {
        // Initialize the database
        companyLicenseWestRepository.saveAndFlush(companyLicenseWest);

        int databaseSizeBeforeDelete = companyLicenseWestRepository.findAll().size();

        // Delete the companyLicenseWest
        restCompanyLicenseWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyLicenseWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyLicenseWest> companyLicenseWestList = companyLicenseWestRepository.findAll();
        assertThat(companyLicenseWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
