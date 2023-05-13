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
import ro.west.domain.UserTypeWest;
import ro.west.repository.UserTypeWestRepository;
import ro.west.service.dto.UserTypeWestDTO;
import ro.west.service.mapper.UserTypeWestMapper;

/**
 * Integration tests for the {@link UserTypeWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserTypeWestResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/user-type-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserTypeWestRepository userTypeWestRepository;

    @Autowired
    private UserTypeWestMapper userTypeWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserTypeWestMockMvc;

    private UserTypeWest userTypeWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTypeWest createEntity(EntityManager em) {
        UserTypeWest userTypeWest = new UserTypeWest().code(DEFAULT_CODE).discount(DEFAULT_DISCOUNT);
        return userTypeWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTypeWest createUpdatedEntity(EntityManager em) {
        UserTypeWest userTypeWest = new UserTypeWest().code(UPDATED_CODE).discount(UPDATED_DISCOUNT);
        return userTypeWest;
    }

    @BeforeEach
    public void initTest() {
        userTypeWest = createEntity(em);
    }

    @Test
    @Transactional
    void createUserTypeWest() throws Exception {
        int databaseSizeBeforeCreate = userTypeWestRepository.findAll().size();
        // Create the UserTypeWest
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);
        restUserTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeCreate + 1);
        UserTypeWest testUserTypeWest = userTypeWestList.get(userTypeWestList.size() - 1);
        assertThat(testUserTypeWest.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserTypeWest.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void createUserTypeWestWithExistingId() throws Exception {
        // Create the UserTypeWest with an existing ID
        userTypeWest.setId(1L);
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);

        int databaseSizeBeforeCreate = userTypeWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTypeWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserTypeWests() throws Exception {
        // Initialize the database
        userTypeWestRepository.saveAndFlush(userTypeWest);

        // Get all the userTypeWestList
        restUserTypeWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userTypeWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getUserTypeWest() throws Exception {
        // Initialize the database
        userTypeWestRepository.saveAndFlush(userTypeWest);

        // Get the userTypeWest
        restUserTypeWestMockMvc
            .perform(get(ENTITY_API_URL_ID, userTypeWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userTypeWest.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserTypeWest() throws Exception {
        // Get the userTypeWest
        restUserTypeWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserTypeWest() throws Exception {
        // Initialize the database
        userTypeWestRepository.saveAndFlush(userTypeWest);

        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();

        // Update the userTypeWest
        UserTypeWest updatedUserTypeWest = userTypeWestRepository.findById(userTypeWest.getId()).get();
        // Disconnect from session so that the updates on updatedUserTypeWest are not directly saved in db
        em.detach(updatedUserTypeWest);
        updatedUserTypeWest.code(UPDATED_CODE).discount(UPDATED_DISCOUNT);
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(updatedUserTypeWest);

        restUserTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
        UserTypeWest testUserTypeWest = userTypeWestList.get(userTypeWestList.size() - 1);
        assertThat(testUserTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserTypeWest.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingUserTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();
        userTypeWest.setId(count.incrementAndGet());

        // Create the UserTypeWest
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTypeWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();
        userTypeWest.setId(count.incrementAndGet());

        // Create the UserTypeWest
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();
        userTypeWest.setId(count.incrementAndGet());

        // Create the UserTypeWest
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeWestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserTypeWestWithPatch() throws Exception {
        // Initialize the database
        userTypeWestRepository.saveAndFlush(userTypeWest);

        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();

        // Update the userTypeWest using partial update
        UserTypeWest partialUpdatedUserTypeWest = new UserTypeWest();
        partialUpdatedUserTypeWest.setId(userTypeWest.getId());

        partialUpdatedUserTypeWest.code(UPDATED_CODE).discount(UPDATED_DISCOUNT);

        restUserTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
        UserTypeWest testUserTypeWest = userTypeWestList.get(userTypeWestList.size() - 1);
        assertThat(testUserTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserTypeWest.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateUserTypeWestWithPatch() throws Exception {
        // Initialize the database
        userTypeWestRepository.saveAndFlush(userTypeWest);

        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();

        // Update the userTypeWest using partial update
        UserTypeWest partialUpdatedUserTypeWest = new UserTypeWest();
        partialUpdatedUserTypeWest.setId(userTypeWest.getId());

        partialUpdatedUserTypeWest.code(UPDATED_CODE).discount(UPDATED_DISCOUNT);

        restUserTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTypeWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTypeWest))
            )
            .andExpect(status().isOk());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
        UserTypeWest testUserTypeWest = userTypeWestList.get(userTypeWestList.size() - 1);
        assertThat(testUserTypeWest.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserTypeWest.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingUserTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();
        userTypeWest.setId(count.incrementAndGet());

        // Create the UserTypeWest
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userTypeWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();
        userTypeWest.setId(count.incrementAndGet());

        // Create the UserTypeWest
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserTypeWest() throws Exception {
        int databaseSizeBeforeUpdate = userTypeWestRepository.findAll().size();
        userTypeWest.setId(count.incrementAndGet());

        // Create the UserTypeWest
        UserTypeWestDTO userTypeWestDTO = userTypeWestMapper.toDto(userTypeWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeWestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTypeWest in the database
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserTypeWest() throws Exception {
        // Initialize the database
        userTypeWestRepository.saveAndFlush(userTypeWest);

        int databaseSizeBeforeDelete = userTypeWestRepository.findAll().size();

        // Delete the userTypeWest
        restUserTypeWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, userTypeWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserTypeWest> userTypeWestList = userTypeWestRepository.findAll();
        assertThat(userTypeWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
