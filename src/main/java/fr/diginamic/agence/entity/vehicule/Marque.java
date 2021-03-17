package fr.diginamic.agence.entity.vehicule;

import fr.diginamic.swing.composants.ui.Selectable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Marque implements Selectable {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    protected Long id;
    protected String nom;

    @OneToMany( targetEntity = Vehicule.class, mappedBy = "marque")
    protected List<Vehicule> vehicules = new ArrayList<>();

    public Marque () {}

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
