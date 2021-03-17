package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.vehicule.StatutVehicule;

public class StatutVehiculeDAO extends SourceDAO<StatutVehicule> {

    public StatutVehiculeDAO() {
        super(StatutVehicule.class);
        this.tableName = StatutVehicule.class.getSimpleName();
    }

}
