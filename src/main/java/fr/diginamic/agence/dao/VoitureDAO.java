package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.vehicule.Voiture;

public class VoitureDAO extends VehiculeDAO<Voiture> {

    public VoitureDAO() {
        super();
        this.classe = Voiture.class;
        this.tableName = Voiture.class.getSimpleName();
    }

}
