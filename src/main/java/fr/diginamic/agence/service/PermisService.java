package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.PermisDAO;
import fr.diginamic.agence.entity.client.Permis;

public class PermisService extends SourceService<PermisDAO, Permis> {

    public PermisService() {
        this.entityDAO = new PermisDAO();
    }

}