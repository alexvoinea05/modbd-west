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
import ro.west.domain.CompanyWest;
import ro.west.repository.CompanyWestRepository;
import ro.west.service.dto.CompanyWestDTO;
import ro.west.service.mapper.CompanyWestMapper;

/**
 * Integration tests for the {@link CompanyWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyWestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyWestRepository companyWestRepository;

    @Autowired
    private CompanyWestMapper companyWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyWestMockMvc;

    private CompanyWest companyWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyWest createEntity(EntityManager em) {
        CompanyWest companyWest = new CompanyWest().name(DEFAULT_NAME).identificationNumber(DEFAULT_IDENTIFICATION_NUMBER);
        return companyWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyWest createUpdatedEntity(EntityManager em) {
        CompanyWest companyWest = new CompanyWest().name(UPDATED_NAME).identificationNumber(UPDATED_IDENTIFICATION_NUMBER);
        return companyWest;
    }

    @BeforeEach
    public void initTest() {
        companyWest = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyWest() throws Exception {
        int databaseSizeBeforeCreate = companyWestRepository.findAll().size();
        // Create the CompanyWest
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);
        restCompanyWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyWest testCompanyWest = companyWestList.get(companyWestList.size() - 1);
        assertThat(testCompanyWest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyWest.getIdentificationNumber()).isEqualTo(DEFAULT_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void createCompanyWestWithExistingId() throws Exception {
        // Create the CompanyWest with an existing ID
        companyWest.setId(1L);
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);

        int databaseSizeBeforeCreate = companyWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyWests() throws Exception {
        // Initialize the database
        companyWestRepository.saveAndFlush(companyWest);

        // Get all the companyWestList
        restCompanyWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].identificationNumber").value(hasItem(DEFAULT_IDENTIFICATION_NUMBER)));
    }

    @Test
    @Transactional
    void getCompanyWest() throws Exception {
        // Initialize the database
        companyWestRepository.saveAndFlush(companyWest);

        // Get the companyWest
        restCompanyWestMockMvc
            .perform(get(ENTITY_API_URL_ID, companyWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyWest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.identificationNumber").value(DEFAULT_IDENTIFICATION_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingCompanyWest() throws Exception {
        // Get the companyWest
        restCompanyWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompanyWest() throws Exception {
        // Initialize the database
        companyWestRepository.saveAndFlush(companyWest);

        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();

        // Update the companyWest
        CompanyWest updatedCompanyWest = companyWestRepository.findById(companyWest.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyWest are not directly saved in db
        em.detach(updatedCompanyWest);
        updatedCompanyWest.name(UPDATED_NAME).identificationNumber(UPDATED_IDENTIFICATION_NUMBER);
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(updatedCompanyWest);

        restCompanyWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
        CompanyWest testCompanyWest = companyWestList.get(companyWestList.size() - 1);
        assertThat(testCompanyWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyWest.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingCompanyWest() throws Exception {
        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();
        companyWest.setId(count.incrementAndGet());

        // Create the CompanyWest
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyWest() throws Exception {
        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();
        companyWest.setId(count.incrementAndGet());

        // Create the CompanyWest
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyWest() throws Exception {
        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();
        companyWest.setId(count.incrementAndGet());

        // Create the CompanyWest
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyWestWithPatch() throws Exception {
        // Initialize the database
        companyWestRepository.saveAndFlush(companyWest);

        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();

        // Update the companyWest using partial update
        CompanyWest partialUpdatedCompanyWest = new CompanyWest();
        partialUpdatedCompanyWest.setId(companyWest.getId());

        partialUpdatedCompanyWest.name(UPDATED_NAME).identificationNumber(UPDATED_IDENTIFICATION_NUMBER);

        restCompanyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyWest))
            )
            .andExpect(status().isOk());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
        CompanyWest testCompanyWest = companyWestList.get(companyWestList.size() - 1);
        assertThat(testCompanyWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyWest.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateCompanyWestWithPatch() throws Exception {
        // Initialize the database
        companyWestRepository.saveAndFlush(companyWest);

        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();

        // Update the companyWest using partial update
        CompanyWest partialUpdatedCompanyWest = new CompanyWest();
        partialUpdatedCompanyWest.setId(companyWest.getId());

        partialUpdatedCompanyWest.name(UPDATED_NAME).identificationNumber(UPDATED_IDENTIFICATION_NUMBER);

        restCompanyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyWest))
            )
            .andExpect(status().isOk());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
        CompanyWest testCompanyWest = companyWestList.get(companyWestList.size() - 1);
        assertThat(testCompanyWest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyWest.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyWest() throws Exception {
        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();
        companyWest.setId(count.incrementAndGet());

        // Create the CompanyWest
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyWest() throws Exception {
        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();
        companyWest.setId(count.incrementAndGet());

        // Create the CompanyWest
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyWest() throws Exception {
        int databaseSizeBeforeUpdate = companyWestRepository.findAll().size();
        companyWest.setId(count.incrementAndGet());

        // Create the CompanyWest
        CompanyWestDTO companyWestDTO = companyWestMapper.toDto(companyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyWest in the database
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyWest() throws Exception {
        // Initialize the database
        companyWestRepository.saveAndFlush(companyWest);

        int databaseSizeBeforeDelete = companyWestRepository.findAll().size();

        // Delete the companyWest
        restCompanyWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyWest> companyWestList = companyWestRepository.findAll();
        assertThat(companyWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
