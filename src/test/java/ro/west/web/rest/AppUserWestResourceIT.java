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
import ro.west.domain.AppUserWest;
import ro.west.repository.AppUserWestRepository;
import ro.west.service.dto.AppUserWestDTO;
import ro.west.service.mapper.AppUserWestMapper;

/**
 * Integration tests for the {@link AppUserWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppUserWestResourceIT {

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_CNP = "AAAAAAAAAA";
    private static final String UPDATED_CNP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-user-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppUserWestRepository appUserWestRepository;

    @Autowired
    private AppUserWestMapper appUserWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppUserWestMockMvc;

    private AppUserWest appUserWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppUserWest createEntity(EntityManager em) {
        AppUserWest appUserWest = new AppUserWest().password(DEFAULT_PASSWORD).cnp(DEFAULT_CNP);
        return appUserWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppUserWest createUpdatedEntity(EntityManager em) {
        AppUserWest appUserWest = new AppUserWest().password(UPDATED_PASSWORD).cnp(UPDATED_CNP);
        return appUserWest;
    }

    @BeforeEach
    public void initTest() {
        appUserWest = createEntity(em);
    }

    @Test
    @Transactional
    void createAppUserWest() throws Exception {
        int databaseSizeBeforeCreate = appUserWestRepository.findAll().size();
        // Create the AppUserWest
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);
        restAppUserWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeCreate + 1);
        AppUserWest testAppUserWest = appUserWestList.get(appUserWestList.size() - 1);
        assertThat(testAppUserWest.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testAppUserWest.getCnp()).isEqualTo(DEFAULT_CNP);
    }

    @Test
    @Transactional
    void createAppUserWestWithExistingId() throws Exception {
        // Create the AppUserWest with an existing ID
        appUserWest.setId(1L);
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);

        int databaseSizeBeforeCreate = appUserWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppUserWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppUserWests() throws Exception {
        // Initialize the database
        appUserWestRepository.saveAndFlush(appUserWest);

        // Get all the appUserWestList
        restAppUserWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appUserWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].cnp").value(hasItem(DEFAULT_CNP)));
    }

    @Test
    @Transactional
    void getAppUserWest() throws Exception {
        // Initialize the database
        appUserWestRepository.saveAndFlush(appUserWest);

        // Get the appUserWest
        restAppUserWestMockMvc
            .perform(get(ENTITY_API_URL_ID, appUserWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appUserWest.getId().intValue()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.cnp").value(DEFAULT_CNP));
    }

    @Test
    @Transactional
    void getNonExistingAppUserWest() throws Exception {
        // Get the appUserWest
        restAppUserWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppUserWest() throws Exception {
        // Initialize the database
        appUserWestRepository.saveAndFlush(appUserWest);

        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();

        // Update the appUserWest
        AppUserWest updatedAppUserWest = appUserWestRepository.findById(appUserWest.getId()).get();
        // Disconnect from session so that the updates on updatedAppUserWest are not directly saved in db
        em.detach(updatedAppUserWest);
        updatedAppUserWest.password(UPDATED_PASSWORD).cnp(UPDATED_CNP);
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(updatedAppUserWest);

        restAppUserWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appUserWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
        AppUserWest testAppUserWest = appUserWestList.get(appUserWestList.size() - 1);
        assertThat(testAppUserWest.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAppUserWest.getCnp()).isEqualTo(UPDATED_CNP);
    }

    @Test
    @Transactional
    void putNonExistingAppUserWest() throws Exception {
        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();
        appUserWest.setId(count.incrementAndGet());

        // Create the AppUserWest
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppUserWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appUserWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppUserWest() throws Exception {
        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();
        appUserWest.setId(count.incrementAndGet());

        // Create the AppUserWest
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppUserWest() throws Exception {
        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();
        appUserWest.setId(count.incrementAndGet());

        // Create the AppUserWest
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appUserWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppUserWestWithPatch() throws Exception {
        // Initialize the database
        appUserWestRepository.saveAndFlush(appUserWest);

        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();

        // Update the appUserWest using partial update
        AppUserWest partialUpdatedAppUserWest = new AppUserWest();
        partialUpdatedAppUserWest.setId(appUserWest.getId());

        partialUpdatedAppUserWest.password(UPDATED_PASSWORD).cnp(UPDATED_CNP);

        restAppUserWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppUserWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppUserWest))
            )
            .andExpect(status().isOk());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
        AppUserWest testAppUserWest = appUserWestList.get(appUserWestList.size() - 1);
        assertThat(testAppUserWest.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAppUserWest.getCnp()).isEqualTo(UPDATED_CNP);
    }

    @Test
    @Transactional
    void fullUpdateAppUserWestWithPatch() throws Exception {
        // Initialize the database
        appUserWestRepository.saveAndFlush(appUserWest);

        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();

        // Update the appUserWest using partial update
        AppUserWest partialUpdatedAppUserWest = new AppUserWest();
        partialUpdatedAppUserWest.setId(appUserWest.getId());

        partialUpdatedAppUserWest.password(UPDATED_PASSWORD).cnp(UPDATED_CNP);

        restAppUserWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppUserWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppUserWest))
            )
            .andExpect(status().isOk());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
        AppUserWest testAppUserWest = appUserWestList.get(appUserWestList.size() - 1);
        assertThat(testAppUserWest.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAppUserWest.getCnp()).isEqualTo(UPDATED_CNP);
    }

    @Test
    @Transactional
    void patchNonExistingAppUserWest() throws Exception {
        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();
        appUserWest.setId(count.incrementAndGet());

        // Create the AppUserWest
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppUserWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appUserWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppUserWest() throws Exception {
        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();
        appUserWest.setId(count.incrementAndGet());

        // Create the AppUserWest
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppUserWest() throws Exception {
        int databaseSizeBeforeUpdate = appUserWestRepository.findAll().size();
        appUserWest.setId(count.incrementAndGet());

        // Create the AppUserWest
        AppUserWestDTO appUserWestDTO = appUserWestMapper.toDto(appUserWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appUserWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppUserWest in the database
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppUserWest() throws Exception {
        // Initialize the database
        appUserWestRepository.saveAndFlush(appUserWest);

        int databaseSizeBeforeDelete = appUserWestRepository.findAll().size();

        // Delete the appUserWest
        restAppUserWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, appUserWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppUserWest> appUserWestList = appUserWestRepository.findAll();
        assertThat(appUserWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
