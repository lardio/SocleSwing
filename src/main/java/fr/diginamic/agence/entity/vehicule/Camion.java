package fr.diginamic.agence.entity.vehicule;

import javax.persistence.Entity;

@Entity
public class Camion extends Vehicule {

    protected Integer volumeDisponible;

    public Camion () {
        super();
        this.nom = this.getClass().getSimpleName();
    }

    public Integer getVolumeDisponible() {
        return volumeDisponible;
    }

    public void setVolumeDisponible(Integer volumeDisponible) {
        this.volumeDisponible = volumeDisponible;
    }
}
