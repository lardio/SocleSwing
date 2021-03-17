package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.StatutVehiculeDAO;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;

public class StatutVehiculeService extends SourceService<StatutVehiculeDAO, StatutVehicule> {

    public StatutVehiculeService() {
        this.entityDAO = new StatutVehiculeDAO();
    }

}
