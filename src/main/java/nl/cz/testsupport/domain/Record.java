package nl.cz.testsupport.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Record.
 */
@Entity
@Table(name = "record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "debiteur")
    private String debiteur;

    @Column(name = "veroorzaker")
    private String veroorzaker;

    @Column(name = "overeenkomst")
    private String overeenkomst;

    @Column(name = "datum_ingang")
    private String datumIngang;

    @Column(name = "datum_einde")
    private String datumEinde;

    @ManyToOne
    @JsonIgnoreProperties(value = "records", allowSetters = true)
    private Boekingbestand boekingbestand;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebiteur() {
        return debiteur;
    }

    public Record debiteur(String debiteur) {
        this.debiteur = debiteur;
        return this;
    }

    public void setDebiteur(String debiteur) {
        this.debiteur = debiteur;
    }

    public String getVeroorzaker() {
        return veroorzaker;
    }

    public Record veroorzaker(String veroorzaker) {
        this.veroorzaker = veroorzaker;
        return this;
    }

    public void setVeroorzaker(String veroorzaker) {
        this.veroorzaker = veroorzaker;
    }

    public String getOvereenkomst() {
        return overeenkomst;
    }

    public Record overeenkomst(String overeenkomst) {
        this.overeenkomst = overeenkomst;
        return this;
    }

    public void setOvereenkomst(String overeenkomst) {
        this.overeenkomst = overeenkomst;
    }

    public String getDatumIngang() {
        return datumIngang;
    }

    public Record datumIngang(String datumIngang) {
        this.datumIngang = datumIngang;
        return this;
    }

    public void setDatumIngang(String datumIngang) {
        this.datumIngang = datumIngang;
    }

    public String getDatumEinde() {
        return datumEinde;
    }

    public Record datumEinde(String datumEinde) {
        this.datumEinde = datumEinde;
        return this;
    }

    public void setDatumEinde(String datumEinde) {
        this.datumEinde = datumEinde;
    }

    public Boekingbestand getBoekingbestand() {
        return boekingbestand;
    }

    public Record boekingbestand(Boekingbestand boekingbestand) {
        this.boekingbestand = boekingbestand;
        return this;
    }

    public void setBoekingbestand(Boekingbestand boekingbestand) {
        this.boekingbestand = boekingbestand;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Record)) {
            return false;
        }
        return id != null && id.equals(((Record) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Record{" +
            "id=" + getId() +
            ", debiteur='" + getDebiteur() + "'" +
            ", veroorzaker='" + getVeroorzaker() + "'" +
            ", overeenkomst='" + getOvereenkomst() + "'" +
            ", datumIngang='" + getDatumIngang() + "'" +
            ", datumEinde='" + getDatumEinde() + "'" +
            "}";
    }
}
