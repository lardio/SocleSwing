package fr.diginamic.agence.entity.vehicule;

import javax.persistence.Entity;

@Entity
public class Voiture extends Vehicule {

    protected Integer nombrePlaces;

    public Voiture () {
        super();
        this.nom = this.getClass().getSimpleName();
    }

    public Integer getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(Integer nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }
}
