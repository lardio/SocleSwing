package fr.diginamic.agence.entity.vehicule;

import fr.diginamic.agence.entity.reservation.Reservation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Vehicule {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Long kilometrage;

    @Column (name = "immatriculation", nullable = false, unique = true)
    protected String immatriculation;
    protected String nom;
    protected StatutVehicule statut;
    protected String modele;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn (name = "id_marque", nullable = false)
    protected Marque marque;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn (name = "id_type")
    protected TypeVehicule typeVehicule;

    @OneToMany( targetEntity = Maintenance.class, mappedBy = "vehicule", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    protected List<Maintenance> listeMaintenance;

    @OneToMany( targetEntity = Reservation.class, mappedBy = "vehicule", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    protected List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule")
    protected List<Commentaire> commentaires = new ArrayList<>();

    public Vehicule() {
        this.statut = StatutVehicule.DISPONIBLE;
    };

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Maintenance> getListeMaintenance() {
        return listeMaintenance;
    }

    public void setListeMaintenance(List<Maintenance> listeMaintenance) {
        this.listeMaintenance = listeMaintenance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(Long kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public StatutVehicule getStatut() {
        return statut;
    }

    public void setStatut(StatutVehicule statut) {
        this.statut = statut;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }


}
