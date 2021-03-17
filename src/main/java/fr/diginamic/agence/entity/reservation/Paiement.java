package fr.diginamic.agence.entity.reservation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Paiement {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn (name = "facture_id")
    protected Facture facture;
    protected ModePaiement modePaiement;
    protected LocalDate datePaiement;

    public Paiement() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }
}
