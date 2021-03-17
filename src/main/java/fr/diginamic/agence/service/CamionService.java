package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.CamionDAO;
import fr.diginamic.agence.entity.vehicule.Camion;

public class CamionService extends SourceService<CamionDAO, Camion> {

    public CamionService() {
        this.entityDAO = new CamionDAO();
    }

}
