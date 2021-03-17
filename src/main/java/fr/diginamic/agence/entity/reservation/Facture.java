package fr.diginamic.agence.entity.reservation;

import fr.diginamic.agence.entity.client.Client;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Facture {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Double cout;
    protected StatutFacture statut;
    protected Long nbJourLocation;
    protected Long numero;

    @Transient
    protected Map<String, String> recap;

    @OneToOne
    @JoinColumn (name = "id_reservation")
    protected Reservation reservation;

    @OneToOne (cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn (name = "paiement_id")
    protected Paiement paiement;

    @OneToOne
    @JoinColumn (name = "client_id")
    protected Client client;

    public Facture () {
        this.statut = StatutFacture.ATTENTE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCout() {
        return cout;
    }

    public void setCout(Double cout) {
        this.cout = cout;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getNbJourLocation() {
        return nbJourLocation;
    }

    public void setNbJourLocation(Long nbJourLocation) {
        this.nbJourLocation = nbJourLocation;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Map<String, String> getRecap() {
        return recap;
    }

    public void setRecap(Map<String, String> recap) {
        this.recap = recap;
    }
}
