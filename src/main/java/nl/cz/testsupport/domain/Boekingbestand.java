package nl.cz.testsupport.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import nl.cz.testsupport.domain.enumeration.Label;

/**
 * A Boekingbestand.
 */
@Entity
@Table(name = "boekingbestand")
public class Boekingbestand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kenmerk")
    private String kenmerk;

    @Enumerated(EnumType.STRING)
    @Column(name = "label")
    private Label label;

    @OneToMany(mappedBy = "boekingbestand")
    private Set<Record> records = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKenmerk() {
        return kenmerk;
    }

    public Boekingbestand kenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public Label getLabel() {
        return label;
    }

    public Boekingbestand label(Label label) {
        this.label = label;
        return this;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public Boekingbestand records(Set<Record> records) {
        this.records = records;
        return this;
    }

    public Boekingbestand addRecord(Record record) {
        this.records.add(record);
        record.setBoekingbestand(this);
        return this;
    }

    public Boekingbestand removeRecord(Record record) {
        this.records.remove(record);
        record.setBoekingbestand(null);
        return this;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Boekingbestand)) {
            return false;
        }
        return id != null && id.equals(((Boekingbestand) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Boekingbestand{" +
            "id=" + getId() +
            ", kenmerk='" + getKenmerk() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
