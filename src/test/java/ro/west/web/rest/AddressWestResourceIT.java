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
import ro.west.domain.AddressWest;
import ro.west.repository.AddressWestRepository;
import ro.west.service.dto.AddressWestDTO;
import ro.west.service.mapper.AddressWestMapper;

/**
 * Integration tests for the {@link AddressWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressWestResourceIT {

    private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIPCODE = "BBBBBBBBBB";

    private static final Long DEFAULT_CITY_ID = 1L;
    private static final Long UPDATED_CITY_ID = 2L;

    private static final String ENTITY_API_URL = "/api/address-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressWestRepository addressWestRepository;

    @Autowired
    private AddressWestMapper addressWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressWestMockMvc;

    private AddressWest addressWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressWest createEntity(EntityManager em) {
        AddressWest addressWest = new AddressWest()
            .streetNumber(DEFAULT_STREET_NUMBER)
            .street(DEFAULT_STREET)
            .zipcode(DEFAULT_ZIPCODE)
            .cityId(DEFAULT_CITY_ID);
        return addressWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressWest createUpdatedEntity(EntityManager em) {
        AddressWest addressWest = new AddressWest()
            .streetNumber(UPDATED_STREET_NUMBER)
            .street(UPDATED_STREET)
            .zipcode(UPDATED_ZIPCODE)
            .cityId(UPDATED_CITY_ID);
        return addressWest;
    }

    @BeforeEach
    public void initTest() {
        addressWest = createEntity(em);
    }

    @Test
    @Transactional
    void createAddressWest() throws Exception {
        int databaseSizeBeforeCreate = addressWestRepository.findAll().size();
        // Create the AddressWest
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);
        restAddressWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeCreate + 1);
        AddressWest testAddressWest = addressWestList.get(addressWestList.size() - 1);
        assertThat(testAddressWest.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testAddressWest.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddressWest.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testAddressWest.getCityId()).isEqualTo(DEFAULT_CITY_ID);
    }

    @Test
    @Transactional
    void createAddressWestWithExistingId() throws Exception {
        // Create the AddressWest with an existing ID
        addressWest.setId(1L);
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);

        int databaseSizeBeforeCreate = addressWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAddressWests() throws Exception {
        // Initialize the database
        addressWestRepository.saveAndFlush(addressWest);

        // Get all the addressWestList
        restAddressWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(DEFAULT_CITY_ID.intValue())));
    }

    @Test
    @Transactional
    void getAddressWest() throws Exception {
        // Initialize the database
        addressWestRepository.saveAndFlush(addressWest);

        // Get the addressWest
        restAddressWestMockMvc
            .perform(get(ENTITY_API_URL_ID, addressWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addressWest.getId().intValue()))
            .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.cityId").value(DEFAULT_CITY_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAddressWest() throws Exception {
        // Get the addressWest
        restAddressWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddressWest() throws Exception {
        // Initialize the database
        addressWestRepository.saveAndFlush(addressWest);

        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();

        // Update the addressWest
        AddressWest updatedAddressWest = addressWestRepository.findById(addressWest.getId()).get();
        // Disconnect from session so that the updates on updatedAddressWest are not directly saved in db
        em.detach(updatedAddressWest);
        updatedAddressWest.streetNumber(UPDATED_STREET_NUMBER).street(UPDATED_STREET).zipcode(UPDATED_ZIPCODE).cityId(UPDATED_CITY_ID);
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(updatedAddressWest);

        restAddressWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
        AddressWest testAddressWest = addressWestList.get(addressWestList.size() - 1);
        assertThat(testAddressWest.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testAddressWest.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddressWest.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testAddressWest.getCityId()).isEqualTo(UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void putNonExistingAddressWest() throws Exception {
        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();
        addressWest.setId(count.incrementAndGet());

        // Create the AddressWest
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddressWest() throws Exception {
        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();
        addressWest.setId(count.incrementAndGet());

        // Create the AddressWest
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddressWest() throws Exception {
        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();
        addressWest.setId(count.incrementAndGet());

        // Create the AddressWest
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressWestWithPatch() throws Exception {
        // Initialize the database
        addressWestRepository.saveAndFlush(addressWest);

        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();

        // Update the addressWest using partial update
        AddressWest partialUpdatedAddressWest = new AddressWest();
        partialUpdatedAddressWest.setId(addressWest.getId());

        partialUpdatedAddressWest.zipcode(UPDATED_ZIPCODE).cityId(UPDATED_CITY_ID);

        restAddressWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddressWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddressWest))
            )
            .andExpect(status().isOk());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
        AddressWest testAddressWest = addressWestList.get(addressWestList.size() - 1);
        assertThat(testAddressWest.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testAddressWest.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddressWest.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testAddressWest.getCityId()).isEqualTo(UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void fullUpdateAddressWestWithPatch() throws Exception {
        // Initialize the database
        addressWestRepository.saveAndFlush(addressWest);

        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();

        // Update the addressWest using partial update
        AddressWest partialUpdatedAddressWest = new AddressWest();
        partialUpdatedAddressWest.setId(addressWest.getId());

        partialUpdatedAddressWest
            .streetNumber(UPDATED_STREET_NUMBER)
            .street(UPDATED_STREET)
            .zipcode(UPDATED_ZIPCODE)
            .cityId(UPDATED_CITY_ID);

        restAddressWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddressWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddressWest))
            )
            .andExpect(status().isOk());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
        AddressWest testAddressWest = addressWestList.get(addressWestList.size() - 1);
        assertThat(testAddressWest.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testAddressWest.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddressWest.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testAddressWest.getCityId()).isEqualTo(UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAddressWest() throws Exception {
        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();
        addressWest.setId(count.incrementAndGet());

        // Create the AddressWest
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddressWest() throws Exception {
        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();
        addressWest.setId(count.incrementAndGet());

        // Create the AddressWest
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddressWest() throws Exception {
        int databaseSizeBeforeUpdate = addressWestRepository.findAll().size();
        addressWest.setId(count.incrementAndGet());

        // Create the AddressWest
        AddressWestDTO addressWestDTO = addressWestMapper.toDto(addressWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addressWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddressWest in the database
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddressWest() throws Exception {
        // Initialize the database
        addressWestRepository.saveAndFlush(addressWest);

        int databaseSizeBeforeDelete = addressWestRepository.findAll().size();

        // Delete the addressWest
        restAddressWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, addressWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddressWest> addressWestList = addressWestRepository.findAll();
        assertThat(addressWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
