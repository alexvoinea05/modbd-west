package ro.west.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.JourneyWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourneyWestDTO implements Serializable {

    private Long id;

    private Double distance;

    private Double journeyDuration;

    private ZonedDateTime actualDepartureTime;

    private ZonedDateTime plannedDepartureTime;

    private ZonedDateTime actualArrivalTime;

    private ZonedDateTime plannedArrivalTime;

    private Double ticketPrice;

    private Integer numberOfStops;

    private Double timeOfStops;

    private Double minutesLate;

    private Long journeyStatusId;

    private Long trainId;

    private Long companyId;

    private Long departureRailwayStationId;

    private Long arrivalRailwayStationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getJourneyDuration() {
        return journeyDuration;
    }

    public void setJourneyDuration(Double journeyDuration) {
        this.journeyDuration = journeyDuration;
    }

    public ZonedDateTime getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(ZonedDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public ZonedDateTime getPlannedDepartureTime() {
        return plannedDepartureTime;
    }

    public void setPlannedDepartureTime(ZonedDateTime plannedDepartureTime) {
        this.plannedDepartureTime = plannedDepartureTime;
    }

    public ZonedDateTime getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(ZonedDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public ZonedDateTime getPlannedArrivalTime() {
        return plannedArrivalTime;
    }

    public void setPlannedArrivalTime(ZonedDateTime plannedArrivalTime) {
        this.plannedArrivalTime = plannedArrivalTime;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(Integer numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public Double getTimeOfStops() {
        return timeOfStops;
    }

    public void setTimeOfStops(Double timeOfStops) {
        this.timeOfStops = timeOfStops;
    }

    public Double getMinutesLate() {
        return minutesLate;
    }

    public void setMinutesLate(Double minutesLate) {
        this.minutesLate = minutesLate;
    }

    public Long getJourneyStatusId() {
        return journeyStatusId;
    }

    public void setJourneyStatusId(Long journeyStatusId) {
        this.journeyStatusId = journeyStatusId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartureRailwayStationId() {
        return departureRailwayStationId;
    }

    public void setDepartureRailwayStationId(Long departureRailwayStationId) {
        this.departureRailwayStationId = departureRailwayStationId;
    }

    public Long getArrivalRailwayStationId() {
        return arrivalRailwayStationId;
    }

    public void setArrivalRailwayStationId(Long arrivalRailwayStationId) {
        this.arrivalRailwayStationId = arrivalRailwayStationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JourneyWestDTO)) {
            return false;
        }

        JourneyWestDTO journeyWestDTO = (JourneyWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, journeyWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourneyWestDTO{" +
            "id=" + getId() +
            ", distance=" + getDistance() +
            ", journeyDuration=" + getJourneyDuration() +
            ", actualDepartureTime='" + getActualDepartureTime() + "'" +
            ", plannedDepartureTime='" + getPlannedDepartureTime() + "'" +
            ", actualArrivalTime='" + getActualArrivalTime() + "'" +
            ", plannedArrivalTime='" + getPlannedArrivalTime() + "'" +
            ", ticketPrice=" + getTicketPrice() +
            ", numberOfStops=" + getNumberOfStops() +
            ", timeOfStops=" + getTimeOfStops() +
            ", minutesLate=" + getMinutesLate() +
            ", journeyStatusId=" + getJourneyStatusId() +
            ", trainId=" + getTrainId() +
            ", companyId=" + getCompanyId() +
            ", departureRailwayStationId=" + getDepartureRailwayStationId() +
            ", arrivalRailwayStationId=" + getArrivalRailwayStationId() +
            "}";
    }
}
