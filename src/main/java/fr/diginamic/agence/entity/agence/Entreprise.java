package fr.diginamic.agence.entity.agence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Entreprise {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Double compta;
    protected String nom;

    public Entreprise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCompta() {
        return compta;
    }

    public void setCompta(Double compta) {
        this.compta = compta;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
