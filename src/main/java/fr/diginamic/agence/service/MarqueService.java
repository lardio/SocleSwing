package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.MarqueDAO;
import fr.diginamic.agence.entity.vehicule.Marque;

public class MarqueService extends SourceService<MarqueDAO, Marque> {

    public MarqueService() {
        this.entityDAO = new MarqueDAO();
    }

    //protected boolean checkIfAlreadyHaveMarque

}
