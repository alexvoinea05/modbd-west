package ro.west.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A TrainWest.
 */
@Entity
@Table(name = "train_west")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainWest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Column(name = "fuel_type_id")
    private Long fuelTypeId;

    @Column(name = "train_type_id")
    private Long trainTypeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrainWest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TrainWest code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public TrainWest numberOfSeats(Long numberOfSeats) {
        this.setNumberOfSeats(numberOfSeats);
        return this;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Long getFuelTypeId() {
        return this.fuelTypeId;
    }

    public TrainWest fuelTypeId(Long fuelTypeId) {
        this.setFuelTypeId(fuelTypeId);
        return this;
    }

    public void setFuelTypeId(Long fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public Long getTrainTypeId() {
        return this.trainTypeId;
    }

    public TrainWest trainTypeId(Long trainTypeId) {
        this.setTrainTypeId(trainTypeId);
        return this;
    }

    public void setTrainTypeId(Long trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainWest)) {
            return false;
        }
        return id != null && id.equals(((TrainWest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainWest{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", numberOfSeats=" + getNumberOfSeats() +
            ", fuelTypeId=" + getFuelTypeId() +
            ", trainTypeId=" + getTrainTypeId() +
            "}";
    }
}
