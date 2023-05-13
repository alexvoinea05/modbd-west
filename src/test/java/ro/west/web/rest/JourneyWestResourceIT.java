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
import ro.west.domain.JourneyWest;
import ro.west.repository.JourneyWestRepository;
import ro.west.service.dto.JourneyWestDTO;
import ro.west.service.mapper.JourneyWestMapper;

/**
 * Integration tests for the {@link JourneyWestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JourneyWestResourceIT {

    private static final Double DEFAULT_DISTANCE = 1D;
    private static final Double UPDATED_DISTANCE = 2D;

    private static final Double DEFAULT_JOURNEY_DURATION = 1D;
    private static final Double UPDATED_JOURNEY_DURATION = 2D;

    private static final ZonedDateTime DEFAULT_ACTUAL_DEPARTURE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_DEPARTURE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_DEPARTURE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_DEPARTURE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACTUAL_ARRIVAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_ARRIVAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_TICKET_PRICE = 1D;
    private static final Double UPDATED_TICKET_PRICE = 2D;

    private static final Integer DEFAULT_NUMBER_OF_STOPS = 1;
    private static final Integer UPDATED_NUMBER_OF_STOPS = 2;

    private static final Double DEFAULT_TIME_OF_STOPS = 1D;
    private static final Double UPDATED_TIME_OF_STOPS = 2D;

    private static final Double DEFAULT_MINUTES_LATE = 1D;
    private static final Double UPDATED_MINUTES_LATE = 2D;

    private static final Long DEFAULT_JOURNEY_STATUS_ID = 1L;
    private static final Long UPDATED_JOURNEY_STATUS_ID = 2L;

    private static final Long DEFAULT_TRAIN_ID = 1L;
    private static final Long UPDATED_TRAIN_ID = 2L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_DEPARTURE_RAILWAY_STATION_ID = 1L;
    private static final Long UPDATED_DEPARTURE_RAILWAY_STATION_ID = 2L;

    private static final Long DEFAULT_ARRIVAL_RAILWAY_STATION_ID = 1L;
    private static final Long UPDATED_ARRIVAL_RAILWAY_STATION_ID = 2L;

    private static final String ENTITY_API_URL = "/api/journey-wests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JourneyWestRepository journeyWestRepository;

    @Autowired
    private JourneyWestMapper journeyWestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJourneyWestMockMvc;

    private JourneyWest journeyWest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyWest createEntity(EntityManager em) {
        JourneyWest journeyWest = new JourneyWest()
            .distance(DEFAULT_DISTANCE)
            .journeyDuration(DEFAULT_JOURNEY_DURATION)
            .actualDepartureTime(DEFAULT_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(DEFAULT_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(DEFAULT_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(DEFAULT_PLANNED_ARRIVAL_TIME)
            .ticketPrice(DEFAULT_TICKET_PRICE)
            .numberOfStops(DEFAULT_NUMBER_OF_STOPS)
            .timeOfStops(DEFAULT_TIME_OF_STOPS)
            .minutesLate(DEFAULT_MINUTES_LATE)
            .journeyStatusId(DEFAULT_JOURNEY_STATUS_ID)
            .trainId(DEFAULT_TRAIN_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .departureRailwayStationId(DEFAULT_DEPARTURE_RAILWAY_STATION_ID)
            .arrivalRailwayStationId(DEFAULT_ARRIVAL_RAILWAY_STATION_ID);
        return journeyWest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyWest createUpdatedEntity(EntityManager em) {
        JourneyWest journeyWest = new JourneyWest()
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE)
            .journeyStatusId(UPDATED_JOURNEY_STATUS_ID)
            .trainId(UPDATED_TRAIN_ID)
            .companyId(UPDATED_COMPANY_ID)
            .departureRailwayStationId(UPDATED_DEPARTURE_RAILWAY_STATION_ID)
            .arrivalRailwayStationId(UPDATED_ARRIVAL_RAILWAY_STATION_ID);
        return journeyWest;
    }

    @BeforeEach
    public void initTest() {
        journeyWest = createEntity(em);
    }

    @Test
    @Transactional
    void createJourneyWest() throws Exception {
        int databaseSizeBeforeCreate = journeyWestRepository.findAll().size();
        // Create the JourneyWest
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);
        restJourneyWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeCreate + 1);
        JourneyWest testJourneyWest = journeyWestList.get(journeyWestList.size() - 1);
        assertThat(testJourneyWest.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testJourneyWest.getJourneyDuration()).isEqualTo(DEFAULT_JOURNEY_DURATION);
        assertThat(testJourneyWest.getActualDepartureTime()).isEqualTo(DEFAULT_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyWest.getPlannedDepartureTime()).isEqualTo(DEFAULT_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyWest.getActualArrivalTime()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyWest.getPlannedArrivalTime()).isEqualTo(DEFAULT_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyWest.getTicketPrice()).isEqualTo(DEFAULT_TICKET_PRICE);
        assertThat(testJourneyWest.getNumberOfStops()).isEqualTo(DEFAULT_NUMBER_OF_STOPS);
        assertThat(testJourneyWest.getTimeOfStops()).isEqualTo(DEFAULT_TIME_OF_STOPS);
        assertThat(testJourneyWest.getMinutesLate()).isEqualTo(DEFAULT_MINUTES_LATE);
        assertThat(testJourneyWest.getJourneyStatusId()).isEqualTo(DEFAULT_JOURNEY_STATUS_ID);
        assertThat(testJourneyWest.getTrainId()).isEqualTo(DEFAULT_TRAIN_ID);
        assertThat(testJourneyWest.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testJourneyWest.getDepartureRailwayStationId()).isEqualTo(DEFAULT_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyWest.getArrivalRailwayStationId()).isEqualTo(DEFAULT_ARRIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void createJourneyWestWithExistingId() throws Exception {
        // Create the JourneyWest with an existing ID
        journeyWest.setId(1L);
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);

        int databaseSizeBeforeCreate = journeyWestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyWestMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJourneyWests() throws Exception {
        // Initialize the database
        journeyWestRepository.saveAndFlush(journeyWest);

        // Get all the journeyWestList
        restJourneyWestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journeyWest.getId().intValue())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].journeyDuration").value(hasItem(DEFAULT_JOURNEY_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].actualDepartureTime").value(hasItem(sameInstant(DEFAULT_ACTUAL_DEPARTURE_TIME))))
            .andExpect(jsonPath("$.[*].plannedDepartureTime").value(hasItem(sameInstant(DEFAULT_PLANNED_DEPARTURE_TIME))))
            .andExpect(jsonPath("$.[*].actualArrivalTime").value(hasItem(sameInstant(DEFAULT_ACTUAL_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].plannedArrivalTime").value(hasItem(sameInstant(DEFAULT_PLANNED_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].ticketPrice").value(hasItem(DEFAULT_TICKET_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfStops").value(hasItem(DEFAULT_NUMBER_OF_STOPS)))
            .andExpect(jsonPath("$.[*].timeOfStops").value(hasItem(DEFAULT_TIME_OF_STOPS.doubleValue())))
            .andExpect(jsonPath("$.[*].minutesLate").value(hasItem(DEFAULT_MINUTES_LATE.doubleValue())))
            .andExpect(jsonPath("$.[*].journeyStatusId").value(hasItem(DEFAULT_JOURNEY_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainId").value(hasItem(DEFAULT_TRAIN_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].departureRailwayStationId").value(hasItem(DEFAULT_DEPARTURE_RAILWAY_STATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].arrivalRailwayStationId").value(hasItem(DEFAULT_ARRIVAL_RAILWAY_STATION_ID.intValue())));
    }

    @Test
    @Transactional
    void getJourneyWest() throws Exception {
        // Initialize the database
        journeyWestRepository.saveAndFlush(journeyWest);

        // Get the journeyWest
        restJourneyWestMockMvc
            .perform(get(ENTITY_API_URL_ID, journeyWest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(journeyWest.getId().intValue()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.journeyDuration").value(DEFAULT_JOURNEY_DURATION.doubleValue()))
            .andExpect(jsonPath("$.actualDepartureTime").value(sameInstant(DEFAULT_ACTUAL_DEPARTURE_TIME)))
            .andExpect(jsonPath("$.plannedDepartureTime").value(sameInstant(DEFAULT_PLANNED_DEPARTURE_TIME)))
            .andExpect(jsonPath("$.actualArrivalTime").value(sameInstant(DEFAULT_ACTUAL_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.plannedArrivalTime").value(sameInstant(DEFAULT_PLANNED_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.ticketPrice").value(DEFAULT_TICKET_PRICE.doubleValue()))
            .andExpect(jsonPath("$.numberOfStops").value(DEFAULT_NUMBER_OF_STOPS))
            .andExpect(jsonPath("$.timeOfStops").value(DEFAULT_TIME_OF_STOPS.doubleValue()))
            .andExpect(jsonPath("$.minutesLate").value(DEFAULT_MINUTES_LATE.doubleValue()))
            .andExpect(jsonPath("$.journeyStatusId").value(DEFAULT_JOURNEY_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.trainId").value(DEFAULT_TRAIN_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.departureRailwayStationId").value(DEFAULT_DEPARTURE_RAILWAY_STATION_ID.intValue()))
            .andExpect(jsonPath("$.arrivalRailwayStationId").value(DEFAULT_ARRIVAL_RAILWAY_STATION_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingJourneyWest() throws Exception {
        // Get the journeyWest
        restJourneyWestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJourneyWest() throws Exception {
        // Initialize the database
        journeyWestRepository.saveAndFlush(journeyWest);

        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();

        // Update the journeyWest
        JourneyWest updatedJourneyWest = journeyWestRepository.findById(journeyWest.getId()).get();
        // Disconnect from session so that the updates on updatedJourneyWest are not directly saved in db
        em.detach(updatedJourneyWest);
        updatedJourneyWest
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE)
            .journeyStatusId(UPDATED_JOURNEY_STATUS_ID)
            .trainId(UPDATED_TRAIN_ID)
            .companyId(UPDATED_COMPANY_ID)
            .departureRailwayStationId(UPDATED_DEPARTURE_RAILWAY_STATION_ID)
            .arrivalRailwayStationId(UPDATED_ARRIVAL_RAILWAY_STATION_ID);
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(updatedJourneyWest);

        restJourneyWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isOk());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
        JourneyWest testJourneyWest = journeyWestList.get(journeyWestList.size() - 1);
        assertThat(testJourneyWest.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourneyWest.getJourneyDuration()).isEqualTo(UPDATED_JOURNEY_DURATION);
        assertThat(testJourneyWest.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyWest.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyWest.getActualArrivalTime()).isEqualTo(UPDATED_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyWest.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyWest.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testJourneyWest.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourneyWest.getTimeOfStops()).isEqualTo(UPDATED_TIME_OF_STOPS);
        assertThat(testJourneyWest.getMinutesLate()).isEqualTo(UPDATED_MINUTES_LATE);
        assertThat(testJourneyWest.getJourneyStatusId()).isEqualTo(UPDATED_JOURNEY_STATUS_ID);
        assertThat(testJourneyWest.getTrainId()).isEqualTo(UPDATED_TRAIN_ID);
        assertThat(testJourneyWest.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testJourneyWest.getDepartureRailwayStationId()).isEqualTo(UPDATED_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyWest.getArrivalRailwayStationId()).isEqualTo(UPDATED_ARRIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void putNonExistingJourneyWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();
        journeyWest.setId(count.incrementAndGet());

        // Create the JourneyWest
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyWestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJourneyWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();
        journeyWest.setId(count.incrementAndGet());

        // Create the JourneyWest
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyWestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJourneyWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();
        journeyWest.setId(count.incrementAndGet());

        // Create the JourneyWest
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyWestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyWestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJourneyWestWithPatch() throws Exception {
        // Initialize the database
        journeyWestRepository.saveAndFlush(journeyWest);

        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();

        // Update the journeyWest using partial update
        JourneyWest partialUpdatedJourneyWest = new JourneyWest();
        partialUpdatedJourneyWest.setId(journeyWest.getId());

        partialUpdatedJourneyWest
            .distance(UPDATED_DISTANCE)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .arrivalRailwayStationId(UPDATED_ARRIVAL_RAILWAY_STATION_ID);

        restJourneyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyWest))
            )
            .andExpect(status().isOk());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
        JourneyWest testJourneyWest = journeyWestList.get(journeyWestList.size() - 1);
        assertThat(testJourneyWest.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourneyWest.getJourneyDuration()).isEqualTo(DEFAULT_JOURNEY_DURATION);
        assertThat(testJourneyWest.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyWest.getPlannedDepartureTime()).isEqualTo(DEFAULT_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyWest.getActualArrivalTime()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyWest.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyWest.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testJourneyWest.getNumberOfStops()).isEqualTo(DEFAULT_NUMBER_OF_STOPS);
        assertThat(testJourneyWest.getTimeOfStops()).isEqualTo(DEFAULT_TIME_OF_STOPS);
        assertThat(testJourneyWest.getMinutesLate()).isEqualTo(DEFAULT_MINUTES_LATE);
        assertThat(testJourneyWest.getJourneyStatusId()).isEqualTo(DEFAULT_JOURNEY_STATUS_ID);
        assertThat(testJourneyWest.getTrainId()).isEqualTo(DEFAULT_TRAIN_ID);
        assertThat(testJourneyWest.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testJourneyWest.getDepartureRailwayStationId()).isEqualTo(DEFAULT_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyWest.getArrivalRailwayStationId()).isEqualTo(UPDATED_ARRIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void fullUpdateJourneyWestWithPatch() throws Exception {
        // Initialize the database
        journeyWestRepository.saveAndFlush(journeyWest);

        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();

        // Update the journeyWest using partial update
        JourneyWest partialUpdatedJourneyWest = new JourneyWest();
        partialUpdatedJourneyWest.setId(journeyWest.getId());

        partialUpdatedJourneyWest
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE)
            .journeyStatusId(UPDATED_JOURNEY_STATUS_ID)
            .trainId(UPDATED_TRAIN_ID)
            .companyId(UPDATED_COMPANY_ID)
            .departureRailwayStationId(UPDATED_DEPARTURE_RAILWAY_STATION_ID)
            .arrivalRailwayStationId(UPDATED_ARRIVAL_RAILWAY_STATION_ID);

        restJourneyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyWest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyWest))
            )
            .andExpect(status().isOk());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
        JourneyWest testJourneyWest = journeyWestList.get(journeyWestList.size() - 1);
        assertThat(testJourneyWest.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourneyWest.getJourneyDuration()).isEqualTo(UPDATED_JOURNEY_DURATION);
        assertThat(testJourneyWest.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyWest.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyWest.getActualArrivalTime()).isEqualTo(UPDATED_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyWest.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyWest.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testJourneyWest.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourneyWest.getTimeOfStops()).isEqualTo(UPDATED_TIME_OF_STOPS);
        assertThat(testJourneyWest.getMinutesLate()).isEqualTo(UPDATED_MINUTES_LATE);
        assertThat(testJourneyWest.getJourneyStatusId()).isEqualTo(UPDATED_JOURNEY_STATUS_ID);
        assertThat(testJourneyWest.getTrainId()).isEqualTo(UPDATED_TRAIN_ID);
        assertThat(testJourneyWest.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testJourneyWest.getDepartureRailwayStationId()).isEqualTo(UPDATED_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyWest.getArrivalRailwayStationId()).isEqualTo(UPDATED_ARRIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void patchNonExistingJourneyWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();
        journeyWest.setId(count.incrementAndGet());

        // Create the JourneyWest
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, journeyWestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJourneyWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();
        journeyWest.setId(count.incrementAndGet());

        // Create the JourneyWest
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyWestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJourneyWest() throws Exception {
        int databaseSizeBeforeUpdate = journeyWestRepository.findAll().size();
        journeyWest.setId(count.incrementAndGet());

        // Create the JourneyWest
        JourneyWestDTO journeyWestDTO = journeyWestMapper.toDto(journeyWest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyWestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(journeyWestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyWest in the database
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJourneyWest() throws Exception {
        // Initialize the database
        journeyWestRepository.saveAndFlush(journeyWest);

        int databaseSizeBeforeDelete = journeyWestRepository.findAll().size();

        // Delete the journeyWest
        restJourneyWestMockMvc
            .perform(delete(ENTITY_API_URL_ID, journeyWest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourneyWest> journeyWestList = journeyWestRepository.findAll();
        assertThat(journeyWestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
