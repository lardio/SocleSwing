package fr.diginamic.agence.entity.vehicule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Maintenance {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne (cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn (name = "id_vehicule", nullable = false)
    protected Vehicule vehicule;

    protected LocalDate dateDebut;
    protected LocalDate dateFin;
    protected Double cout;

    public Maintenance() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Double getCout() {
        return cout;
    }

    public void setCout(Double cout) {
        this.cout = cout;
    }
}
