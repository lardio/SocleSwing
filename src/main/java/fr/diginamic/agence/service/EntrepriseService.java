package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.EntrepriseDAO;
import fr.diginamic.agence.entity.agence.Entreprise;

public class EntrepriseService extends SourceService<EntrepriseDAO, Entreprise> {
    public EntrepriseService() {
        this.entityDAO = new EntrepriseDAO();
    }

}