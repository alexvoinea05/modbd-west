package ro.west.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.west.domain.TrainWest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainWestDTO implements Serializable {

    private Long id;

    private String code;

    private Long numberOfSeats;

    private Long fuelTypeId;

    private Long trainTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Long getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Long fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public Long getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(Long trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainWestDTO)) {
            return false;
        }

        TrainWestDTO trainWestDTO = (TrainWestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trainWestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainWestDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", numberOfSeats=" + getNumberOfSeats() +
            ", fuelTypeId=" + getFuelTypeId() +
            ", trainTypeId=" + getTrainTypeId() +
            "}";
    }
}
