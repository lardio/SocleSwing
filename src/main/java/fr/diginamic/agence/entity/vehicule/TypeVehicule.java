package fr.diginamic.agence.entity.vehicule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TypeVehicule {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nom;
    protected String sorteVehicule;
    protected Double caution;
    protected Double tarif;

    @OneToMany( targetEntity = Vehicule.class, mappedBy = "typeVehicule")
    protected List<Vehicule> vehicules = new ArrayList<>();

    public TypeVehicule() {}

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

    public Double getCaution() {
        return caution;
    }

    public void setCaution(Double caution) {
        this.caution = caution;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public String getSorteVehicule() {
        return sorteVehicule;
    }

    public void setSorteVehicule(String sorteVehicule) {
        this.sorteVehicule = sorteVehicule;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }
}
