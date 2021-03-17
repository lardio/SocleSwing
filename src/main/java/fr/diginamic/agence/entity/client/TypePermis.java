package fr.diginamic.agence.entity.client;

import fr.diginamic.agence.entity.vehicule.Vehicule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TypePermis {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nom;

    @OneToMany( targetEntity = Permis.class, mappedBy = "typePermis")
    protected List<Permis> listePermis = new ArrayList<>();

    public TypePermis() {}

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

    public List<Permis> getListePermis() {
        return listePermis;
    }

    public void setListePermis(List<Permis> listePermis) {
        this.listePermis = listePermis;
    }
}
