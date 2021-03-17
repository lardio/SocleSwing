package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.TypePermisDAO;
import fr.diginamic.agence.entity.client.TypePermis;

public class TypePermisService extends SourceService<TypePermisDAO, TypePermis> {

    public TypePermisService() {
        this.entityDAO = new TypePermisDAO();
    }

}