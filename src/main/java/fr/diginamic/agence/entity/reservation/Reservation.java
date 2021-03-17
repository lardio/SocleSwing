package fr.diginamic.agence.entity.reservation;

import fr.diginamic.agence.entity.client.Client;
import fr.diginamic.agence.entity.vehicule.Vehicule;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long Id;
    protected LocalDate dateDebut;

    protected Long kilometrageDebut;
    protected LocalDate dateFin;
    protected LocalDate dateRetour;
    protected Long KilometrageFin;
    protected StatutReservation statutReservation;

    @OneToOne (cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn (name = "id_facture")
    protected Facture facture;

    @ManyToOne
    @JoinColumn (name = "id_vehicule", nullable = false)
    protected Vehicule vehicule;

    @ManyToOne
    @JoinColumn (name = "id_client")
    protected Client client;

    public Reservation() {
        this.statutReservation = StatutReservation.A_VENIR;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
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

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public StatutReservation getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(StatutReservation statutReservation) {
        this.statutReservation = statutReservation;
    }

    public Long getKilometrageDebut() {
        return kilometrageDebut;
    }

    public void setKilometrageDebut(Long kilometrageDebut) {
        this.kilometrageDebut = kilometrageDebut;
    }

    public Long getKilometrageFin() {
        return KilometrageFin;
    }

    public void setKilometrageFin(Long kilometrageFin) {
        KilometrageFin = kilometrageFin;
    }
}
