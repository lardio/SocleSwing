package fr.diginamic.agence.entity.client;

import fr.diginamic.agence.entity.reservation.Facture;
import fr.diginamic.agence.entity.reservation.Reservation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nom;
    protected String prenom;

    @OneToOne (cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn (name="adresse")
    protected Adresse adresse;

    @OneToMany (mappedBy = "client")
    protected List<Reservation> reservations = new ArrayList<>();

    @OneToMany (mappedBy = "client")
    protected List<Facture> factures = new ArrayList<>();

    @OneToOne (cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn (name = "id_permis")
    protected Permis permis;

    public Client() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Permis getPermis() {
        return permis;
    }

    public void setPermis(Permis permis) {
        this.permis = permis;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }
}
