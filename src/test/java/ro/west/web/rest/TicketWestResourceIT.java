package ro.west.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ro.west.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import ro.west.domain.TicketWest;
import ro.west.repository.TicketWestRepository;
import ro.west.service.dto.TicketWestDTO;
import ro.west.service.mapper.TicketWestMapper;

/**
 * Integration tests for the {@link TicketWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TicketWestResourceIT {

    private static final Double DEFAULT_FINAL_PRICE = 1D;
    private static final Double UPDATED_FINAL_PRICE = 2D;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_APP_USER_ID = 1L;
    private static final Long UPDATED_APP_USER_ID = 2L;

    private static final Long DEFAULT_JOURNEY_ID = 1L;
    private static final Long UPDATED_JOURNEY_ID = 2L;

    private static final String ENTITY_API_URL = "/api/ticket-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TicketWestRepository ticketWestRepository;

    @Autowired
    private TicketWestMapper ticketWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketWestMockMvc;

    private TicketWest ticketWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketWest createEntity(EntityManager em) {
        TicketWest ticketWest = new TicketWest()
            .finalPrice(DEFAULT_FINAL_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .time(DEFAULT_TIME)
            .appUserId(DEFAULT_APP_USER_ID)
            .journeyId(DEFAULT_JOURNEY_ID);
        return ticketWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketWest createUpdatedEntity(EntityManager em) {
        TicketWest ticketWest = new TicketWest()
            .finalPrice(UPDATED_FINAL_PRICE)
            .quantity(UPDATED_QUANTITY)
            .time(UPDATED_TIME)
            .appUserId(UPDATED_APP_USER_ID)
            .journeyId(UPDATED_JOURNEY_ID);
        return ticketWest;
    }

    @BeforeEach
    public void initTest() {
        ticketWest = createEntity(em);
    }

    @Test
    @Transactional
    void createTicketWest() throws Exception {
        int databaseSizeBeforeCreate = ticketWestRepository.findAll().size();
        // Create the TicketWest
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);
        restTicketWestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketWestDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeCreate + 1);
        TicketWest testTicketWest = ticketWestList.get(ticketWestList.size() - 1);
        assertThat(testTicketWest.getFinalPrice()).isEqualTo(DEFAULT_FINAL_PRICE);
        assertThat(testTicketWest.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTicketWest.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTicketWest.getAppUserId()).isEqualTo(DEFAULT_APP_USER_ID);
        assertThat(testTicketWest.getJourneyId()).isEqualTo(DEFAULT_JOURNEY_ID);
    }

    @Test
    @Transactional
    void createTicketWestWithExistingId() throws Exception {
        // Create the TicketWest with an existing ID
        ticketWest.setId(1L);
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);

        int databaseSizeBeforeCreate = ticketWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketWestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketWestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTicketWests() throws Exception {
        // Initialize the database
        ticketWestRepository.saveAndFlush(ticketWest);

        // Get all the ticketWestList
        restTicketWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].finalPrice").value(hasItem(DEFAULT_FINAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].appUserId").value(hasItem(DEFAULT_APP_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].journeyId").value(hasItem(DEFAULT_JOURNEY_ID.intValue())));
    }

    @Test
    @Transactional
    void getTicketWest() throws Exception {
        // Initialize the database
        ticketWestRepository.saveAndFlush(ticketWest);

        // Get the ticketWest
        restTicketWestMockMvc
            .perform(get(ENTITY_API_URL_ID, ticketWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketWest.getId().intValue()))
            .andExpect(jsonPath("$.finalPrice").value(DEFAULT_FINAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.appUserId").value(DEFAULT_APP_USER_ID.intValue()))
            .andExpect(jsonPath("$.journeyId").value(DEFAULT_JOURNEY_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTicketWest() throws Exception {
        // Get the ticketWest
        restTicketWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTicketWest() throws Exception {
        // Initialize the database
        ticketWestRepository.saveAndFlush(ticketWest);

        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();

        // Update the ticketWest
        TicketWest updatedTicketWest = ticketWestRepository.findById(ticketWest.getId()).get();
        // Disconnect from session so that the updates on updatedTicketWest are not directly saved in db
        em.detach(updatedTicketWest);
        updatedTicketWest
            .finalPrice(UPDATED_FINAL_PRICE)
            .quantity(UPDATED_QUANTITY)
            .time(UPDATED_TIME)
            .appUserId(UPDATED_APP_USER_ID)
            .journeyId(UPDATED_JOURNEY_ID);
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(updatedTicketWest);

        restTicketWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
        TicketWest testTicketWest = ticketWestList.get(ticketWestList.size() - 1);
        assertThat(testTicketWest.getFinalPrice()).isEqualTo(UPDATED_FINAL_PRICE);
        assertThat(testTicketWest.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTicketWest.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTicketWest.getAppUserId()).isEqualTo(UPDATED_APP_USER_ID);
        assertThat(testTicketWest.getJourneyId()).isEqualTo(UPDATED_JOURNEY_ID);
    }

    @Test
    @Transactional
    void putNonExistingTicketWest() throws Exception {
        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();
        ticketWest.setId(count.incrementAndGet());

        // Create the TicketWest
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicketWest() throws Exception {
        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();
        ticketWest.setId(count.incrementAndGet());

        // Create the TicketWest
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicketWest() throws Exception {
        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();
        ticketWest.setId(count.incrementAndGet());

        // Create the TicketWest
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketWestWithPatch() throws Exception {
        // Initialize the database
        ticketWestRepository.saveAndFlush(ticketWest);

        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();

        // Update the ticketWest using partial update
        TicketWest partialUpdatedTicketWest = new TicketWest();
        partialUpdatedTicketWest.setId(ticketWest.getId());

        partialUpdatedTicketWest.finalPrice(UPDATED_FINAL_PRICE).journeyId(UPDATED_JOURNEY_ID);

        restTicketWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicketWest))
            )
            .andExpect(status().isOk());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
        TicketWest testTicketWest = ticketWestList.get(ticketWestList.size() - 1);
        assertThat(testTicketWest.getFinalPrice()).isEqualTo(UPDATED_FINAL_PRICE);
        assertThat(testTicketWest.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTicketWest.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTicketWest.getAppUserId()).isEqualTo(DEFAULT_APP_USER_ID);
        assertThat(testTicketWest.getJourneyId()).isEqualTo(UPDATED_JOURNEY_ID);
    }

    @Test
    @Transactional
    void fullUpdateTicketWestWithPatch() throws Exception {
        // Initialize the database
        ticketWestRepository.saveAndFlush(ticketWest);

        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();

        // Update the ticketWest using partial update
        TicketWest partialUpdatedTicketWest = new TicketWest();
        partialUpdatedTicketWest.setId(ticketWest.getId());

        partialUpdatedTicketWest
            .finalPrice(UPDATED_FINAL_PRICE)
            .quantity(UPDATED_QUANTITY)
            .time(UPDATED_TIME)
            .appUserId(UPDATED_APP_USER_ID)
            .journeyId(UPDATED_JOURNEY_ID);

        restTicketWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicketWest))
            )
            .andExpect(status().isOk());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
        TicketWest testTicketWest = ticketWestList.get(ticketWestList.size() - 1);
        assertThat(testTicketWest.getFinalPrice()).isEqualTo(UPDATED_FINAL_PRICE);
        assertThat(testTicketWest.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTicketWest.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTicketWest.getAppUserId()).isEqualTo(UPDATED_APP_USER_ID);
        assertThat(testTicketWest.getJourneyId()).isEqualTo(UPDATED_JOURNEY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTicketWest() throws Exception {
        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();
        ticketWest.setId(count.incrementAndGet());

        // Create the TicketWest
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicketWest() throws Exception {
        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();
        ticketWest.setId(count.incrementAndGet());

        // Create the TicketWest
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicketWest() throws Exception {
        int databaseSizeBeforeUpdate = ticketWestRepository.findAll().size();
        ticketWest.setId(count.incrementAndGet());

        // Create the TicketWest
        TicketWestDTO ticketWestDTO = ticketWestMapper.toDto(ticketWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ticketWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketWest in the database
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicketWest() throws Exception {
        // Initialize the database
        ticketWestRepository.saveAndFlush(ticketWest);

        int databaseSizeBeforeDelete = ticketWestRepository.findAll().size();

        // Delete the ticketWest
        restTicketWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticketWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketWest> ticketWestList = ticketWestRepository.findAll();
        assertThat(ticketWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
